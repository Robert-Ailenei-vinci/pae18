package be.vinci.pae.services;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
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

  //private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  //private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;

  @Override
  public List<UserDTO> getAll() {
    String sql = "SELECT * FROM users";
    List<UserDTO> users = new ArrayList<>();
    try (PreparedStatement stmnt = dalServices.getPreparedStatement(sql);
        ResultSet rs = stmnt.executeQuery(sql)) {
      while (rs.next()) {
        UserDTO user = new UserImpl();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
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
  public UserDTO getOne(String email) {
    String sql = "SELECT * FROM users WHERE email = ?";
    return getUserMethodFromDB(sql);
  }


  @Override
  public UserDTO createOne(UserDTO user) {
    String sql = "INSERT INTO users (email, password, age, role) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql)) {
      stmt.setString(1, user.getEmail());
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
        user.setEmail(rs.getString("email"));
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