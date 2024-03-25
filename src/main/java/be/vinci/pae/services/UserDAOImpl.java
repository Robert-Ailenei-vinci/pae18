package be.vinci.pae.services;

import static be.vinci.pae.services.utils.Utils.paramStatement;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.OptimisticLockException;
import be.vinci.pae.exception.UserNotFoundException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents an implementation of the {@link UserDAO} interface.
 */
public class UserDAOImpl implements UserDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;
  @Inject
  private SchoolYearDAO schoolYearDAO;

  /**
   * Retrieves all users from the database.
   *
   * @return A list of UserDTO objects representing all users in the database.
   */
  @Override
  public List<UserDTO> getAll() {
    PreparedStatement getAllUsers = dalServices.getPreparedStatement(
        "SELECT u.id_user,u.email, u.role_u, u.last_name, u.first_name,"
            + " u.phone_number, u.psw, u.registration_date,"
            + " u.school_year, s.years_format AS academic_year, u._version "
            + "FROM pae.users u, pae.school_years s WHERE u.school_year=s.id_year");
    List<UserDTO> users = new ArrayList<>();
    try (ResultSet rs = getAllUsers.executeQuery()) {
      while (rs.next()) {
        UserDTO user;
        user = getUserMethodFromDB(rs);
        users.add(user);
      }
    } catch (Exception e) {
      System.out.println(
          e.getMessage()); // pas d erreur possible dans un getAll, a pire renvoie liste vide,
      // donc on affiche pas l'erreur
    }
    return users;
  }

  /**
   * Retrieves a user from the database based on the provided email.
   *
   * @param email The email of the user to retrieve.
   * @return A UserDTO object representing the user with the provided email, or null if not found.
   */
  @Override
  public UserDTO getOne(String email) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT u.id_user, u.email, u.role_u, u.last_name,"
            + " u.first_name, u.phone_number, u.psw,"
            + " u.registration_date, u.school_year,"
            + " s.years_format AS academic_year, u._version "
            + "FROM pae.users u, pae.school_years s WHERE"
            + " u.school_year=s.id_year AND u.email=?")) {
      preparedStatement.setString(1, email);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          return getUserMethodFromDB(rs);
        }
      }
      return null;
    } catch (Exception e) {
      throw new UserNotFoundException("User not found with email " + email, e);
    }
  }

  /**
   * Retrieves a user from the database based on the provided user ID.
   *
   * @param id The ID of the user to retrieve.
   * @return A UserDTO object representing the user with the provided ID, or null if not found.
   */
  @Override
  public UserDTO getOne(int id) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT u.id_user, u.email, u.role_u, u.last_name, "
            + "u.first_name, u.phone_number, u.psw,"
            + " u.registration_date, u.school_year,"
            + " s.years_format AS academic_year, u._version "
            + " FROM pae.users u, pae.school_years s WHERE"
            + " u.school_year=s.id_year AND u.id_user=?")) {
      preparedStatement.setInt(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          return getUserMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      throw new UserNotFoundException("User not found with id " + id, e);
    }
    return null;
  }

  /**
   * Adds a user to the database.
   *
   * @param user The user to add.
   * @return True if the user was added successfully, false otherwise.
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
      throw new FatalError("Error processing result set", e);
    }

    if (idYear == 0) {
      String sql2 = "INSERT INTO pae.school_years (years_format) VALUES (?)";
      try (PreparedStatement stmt = dalServices.getPreparedStatement(sql2)) {
        stmt.setString(1, buildYear());
        stmt.executeUpdate();
      } catch (Exception e) {
        throw new FatalError("Error processing result set", e);
      }
      idYear = getLastInsertedYearId();
    }

    String sql3 = "SELECT max(id_user) FROM pae.users";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql3)) {
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          user.setId(rs.getInt(1) + 1);
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }

    String sql4 = "INSERT INTO pae.users (email, role_u, last_name, first_name, phone_number,"
        + " psw, registration_date, school_year, _version) VALUES (?, ?, ?, ?, ?, ?, ?, ?,0)";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql4)) {
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
      throw new FatalError("Error processing result set", e);
    }
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
      user.setSchoolYear(schoolYearDAO.getOne(rs.getInt("school_year")));
      user.setVersion(rs.getInt("_version"));
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return user;
  }


  /**
   * Change user data.
   *
   * @param user The user to change.
   * @return The user with the changed data
   */
  @Override
  public UserDTO changeUser(UserDTO user) {

    StringBuilder sql = new StringBuilder("UPDATE pae.users SET ");
    List<Object> parameters = new ArrayList<>();

    if (!Objects.equals(user.getEmail(), "")) {
      sql.append("email = ?, ");
      parameters.add(user.getEmail());
    }

    if (!Objects.equals(user.getLastName(), "")) {
      sql.append("last_name = ?, ");
      parameters.add(user.getLastName());
    }

    if (!Objects.equals(user.getFirstName(), "")) {
      sql.append("first_name = ?, ");
      parameters.add(user.getFirstName());
    }

    if (!Objects.equals(user.getPhoneNum(), "")) {
      sql.append("phone_number = ?, ");
      parameters.add(user.getPhoneNum());
    }

    if (!Objects.equals(user.getPassword(), "")) {
      sql.append("psw = ?, ");
      parameters.add(user.getPassword());
    }

    // Remove the last comma and space
    sql.delete(sql.length() - 2, sql.length());

    sql.append(", _version = _version + 1 WHERE email = ? AND _version = ?;");

    parameters.add(user.getEmail());
    getVersionFromDB(user);
    parameters.add(user.getVersion());

    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql.toString())) {
      paramStatement(parameters, stmt);

      if (stmt.executeUpdate() == 0) {
        throw new OptimisticLockException("User was updated by another transaction");
      }

      System.out.println("User updated");
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return getOne(user.getEmail());
  }

  /**
   * Builds the year format.
   *
   * @return The year format.
   */
  private String buildYear() {
    int year;
    if (LocalDate.now().getMonth().compareTo(Month.SEPTEMBER) < 0) {
      year = LocalDate.now().getYear() - 1;
    } else {
      year = LocalDate.now().getYear();
    }
    return year + "-" + (year + 1);
  }

  private int getLastInsertedYearId() {
    String sql = "SELECT MAX(id_year) FROM pae.school_years";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql)) {
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return 0; // return 0 if no id was found
  }

  private void getVersionFromDB(UserDTO user) {
    try (PreparedStatement versionStmt = dalServices.getPreparedStatement(
        "SELECT _version FROM pae.users WHERE email = ?")) {
      versionStmt.setString(1, user.getEmail());
      try (ResultSet rs = versionStmt.executeQuery()) {
        if (rs.next()) {
          // Update the version of the UserDTO object in memory
          user.setVersion(rs.getInt("_version"));
          System.out.println(user.getVersion());
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
  }
}