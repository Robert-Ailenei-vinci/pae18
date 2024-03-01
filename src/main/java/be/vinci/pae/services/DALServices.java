package be.vinci.pae.services;

import java.sql.PreparedStatement;
import org.jvnet.hk2.annotations.Service;

/**
 * DALServices is an interface that provides methods for managing database connections and prepared
 * statements.
 */
@Service
public interface DALServices {


  /**
   * Gets a PreparedStatement object for sending parameterized SQL statements to the database.
   *
   * @param sql the sql request.
   * @return a PreparedStatement object containing the precompiled SQL statement
   */


  PreparedStatement getPreparedStatement(String sql);

  /**
   * Closes the provided PreparedStatement object.
   *
   * @param ps the PreparedStatement object to be closed
   */
  void closePreparedStatement(PreparedStatement ps);

}