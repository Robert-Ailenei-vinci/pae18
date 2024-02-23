package be.vinci.pae.services;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDataServiceImpl is a class that provides methods for interacting with user data.
 */
public class UserDataServiceImpl implements UserDataService {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;

  @Override
  public List<User> getAll() {
    String sql = "SELECT * FROM users";
    List<User> users = new ArrayList<>();
    try (PreparedStatement stmnt = dalServices.getPreparedStatement(sql);
        ResultSet rs = stmnt.executeQuery(sql)) {
      while (rs.next()) {
        User user = new UserImpl();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setAge(rs.getInt("age"));
        user.setRole(rs.getString("role"));
        // Set other user properties here
        users.add(user);
        rs.close();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return users;
  }


  @Override
  public UserDTO getOne(int id) {
    String sql = "SELECT * FROM users WHERE id_utilisateur = ?";
    return getUserMethodFromDB(sql);
  }

  @Override
  public UserDTO getOne(String login) {
    String sql = "SELECT * FROM users WHERE login = ?";
    return getUserMethodFromDB(sql);
  }


  @Override
  public UserDTO createOne(User user) {
    String sql = "INSERT INTO users (login, password, age, role) VALUES (?, ?, ?, ?)";
    try (Connection conn = dalServices.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, user.getLogin());
      stmt.setString(2, user.getPassword());
      stmt.setInt(3, user.getAge());
      stmt.setString(4, user.getRole());
      stmt.executeUpdate();
      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          user.setId(rs.getInt(1));
          return user;
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;

  }

  @Override
  public int nextItemId() {
    String sql = "SELECT MAX(id) FROM users";
    try (Connection conn = dalServices.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      if (rs.next()) {
        return rs.getInt(1) + 1;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return 1;
  }

  private User getUserMethodFromDB(String sql) {
    try (PreparedStatement stmnt = dalServices.getPreparedStatement(sql);
        ResultSet rs = stmnt.executeQuery(sql)) {
      if (rs.next()) {
        User user = new UserImpl();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setAge(rs.getInt("age"));
        user.setRole(rs.getString("role"));
        // Set other user properties here
        return user;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

}