package be.vinci.pae.services.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Utils for DAO Object.
 */
public class utils {

  /**
   * Set the parameter to the SQL statement.
   *
   * @param parameters the parameter to add to the stmt.
   * @param stmt       the SQL statement.
   * @throws SQLException the SQL Exception.
   */
  public static void paramStatement(List<Object> parameters, PreparedStatement stmt)
      throws SQLException {
    for (int i = 0; i < parameters.size(); i++) {
      if (parameters.get(i) instanceof String) {
        stmt.setString(i + 1, (String) parameters.get(i));
      } else if (parameters.get(i) instanceof Integer) {
        stmt.setInt(i + 1, (Integer) parameters.get(i));
      }
    }
  }
}
