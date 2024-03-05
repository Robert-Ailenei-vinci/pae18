package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDataServiceImpl is a class that provides methods for interacting with user data.
 */
public class UserDAOImpl implements UserDAO {

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
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users WHERE email = ?")) {
      preparedStatement.setString(1, email);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          return getUserMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  @Override
  public UserDTO getOne(int id) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users WHERE id_user = ?")) {
      preparedStatement.setInt(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          return getUserMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  private UserDTO getUserMethodFromDB(ResultSet rs) {
    UserDTO user = myDomainFactory.getUser();

    try {
      user.setId(rs.getInt("id_user"));
      user.setEmail(rs.getString("email"));
      user.setPassword(rs.getString("psw"));
      user.setRole(rs.getString("role_u"));
      user.setFirstName(rs.getString("first_name"));
      user.setLastName(rs.getString("last_name"));
      user.setPhoneNum(rs.getString("phone_number"));
      user.setRegistrationDate(rs.getString("registration_date"));
      user.setSchoolYearId(rs.getInt("school_year"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return user;
  }

  /**
   * Adds a user to the database.
   *
   * @param user The user to add.
   * @return True if the user was added, false if not.
   */
  @Override
  public boolean addUser(UserDTO user) {
    int idYear = 0;
    String sql1 = "SELECT id_year FROM pae.school_years WHERE years_format = ?";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql1)) {
      stmt.setString(1, buildYear());
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        idYear = rs.getInt("id_year");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    if (idYear == 0) {
      String sql2 = "INSERT INTO pae.school_years (years_format) VALUES (?)";
      try (PreparedStatement stmt = dalServices.getPreparedStatement(sql2)) {
        stmt.setString(1, buildYear());
        stmt.executeUpdate();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    String sql3 = "INSERT INTO pae.users (email, role_u, last_name, first_name, phone_number,"
        + " psw, registration_date, school_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql3)) {
      stmt.setString(1, user.getEmail());
      stmt.setString(2, user.getRole());
      stmt.setString(3, user.getLastName());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getPhoneNum());
      stmt.setString(6, user.getPassword());
      stmt.setString(7, user.getRegistrationDate());
      stmt.setInt(8, idYear);
      return stmt.executeUpdate() == 1;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  /**
   * Builds the year format.
   *
   * @return The year format.
   */
  public String buildYear() {
    int year;
    if (LocalDate.now().getMonth().compareTo(Month.SEPTEMBER) < 0) {
      year = LocalDate.now().getYear() - 1;
    } else {
      year = LocalDate.now().getYear();
    }
    return year + "-" + (year + 1);
  }
}