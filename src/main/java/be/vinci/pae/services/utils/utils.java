package be.vinci.pae.services.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class utils {

  public static void paramStatment(List<Object> parameters, PreparedStatement stmt)
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
