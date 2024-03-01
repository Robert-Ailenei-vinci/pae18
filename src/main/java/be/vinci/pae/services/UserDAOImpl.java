package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDataServiceImpl is a class that provides methods for interacting with user data.
 */
public class UserDAOImpl implements UserDAO {

  //private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  //private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;

  @Override
  public List<UserDTO> getAll() {
    PreparedStatement getAllUsers = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users");
    List<UserDTO> users = new ArrayList<>();
    try (ResultSet rs = getAllUsers.executeQuery()) {
      while (rs.next()) {
        UserDTO user;
        user = getUserMethodFromDB(rs);
        users.add(user);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return users;
  }


  @Override
  public UserDTO getOne(String email) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users WHERE email = ?");
    try {
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return getUserMethodFromDB(rs);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  /**
   * Adds a user to the database.
   *
   * @param user The user to add.
   * @return True if the user was added, false if not.
   */
  @Override
  public boolean addUser(UserDTO user) {
    String sql = "INSERT INTO pae.users (id_user, email, psw, role_u, first_name, last_name, phone_number, registration_date, school_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql)) {
      stmt.setInt(1, user.getId());
      stmt.setString(2, user.getEmail());
      stmt.setString(3, user.getPassword());
      stmt.setString(4, user.getRole());
      stmt.setString(5, user.getFirstName());
      stmt.setString(6, user.getLastName());
      stmt.setString(7, user.getPhoneNum());
      stmt.setString(8, user.getRegistrationDate());
      stmt.setInt(9, user.getSchoolYear());
      return stmt.executeUpdate() == 1;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  @Override
  public int nextItemId() {
    String sql = "SELECT MAX(id) FROM users";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql);
        ResultSet rs = stmt.executeQuery(sql)) {
      if (rs.next()) {
        return rs.getInt(1) + 1;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return 1;
  }

  private UserDTO getUserMethodFromDB(ResultSet rs) {
    UserDTO user = myDomainFactory.getUser();
    try {
      user.setId(rs.getInt("id_user"));
      user.setEmail(rs.getString("email"));
      user.setPassword(rs.getString("password_u"));
      user.setRole(rs.getString("role_u"));
      user.setFirstName(rs.getString("first_name"));
      user.setLastName(rs.getString("last_name"));
      user.setPhoneNum(rs.getString("phone_number"));
      user.setRegistrationDate(rs.getString("registration_date"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return user;
  }


}