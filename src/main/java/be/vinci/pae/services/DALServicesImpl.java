package be.vinci.pae.services;

import be.vinci.pae.exception.FatalError;
import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class represents an implementation of the {@link DALServices} interface.
 */
public class DALServicesImpl implements DALServices {

  private static final String DATA_BASEURL;

  private static final String DATABASE_USER;

  private static final String DATABASE_PASSWORD;

  static {
    DATA_BASEURL = Config.getProperty("DatabaseFilePath");
    DATABASE_USER = Config.getProperty("DataBaseUser");
    DATABASE_PASSWORD = Config.getProperty("DataBasePswd");
  }

  private Connection connection;

  /**
   * Constructor for DALServicesImpl. It establishes a connection to the database.
   */
  public DALServicesImpl() {
    try {
      this.connection = DriverManager.getConnection(DATA_BASEURL, DATABASE_USER, DATABASE_PASSWORD);
    } catch (SQLException e) {
      System.out.println("Unable to connect to database: " + e.getMessage());
    }
  }

  /**
   * Gets a connection to the database.
   *
   * @return a Connection object representing a connection to the database
   */

  public Connection getConnection() {
    return this.connection;
  }

  /**
   * Closes the provided database connection.
   *
   * @param con the Connection object to be closed
   */

  public void closeConnection(Connection con) {
    try {
      if (con != null && !con.isClosed()) {
        con.close();
      }
    } catch (SQLException e) {
      throw new FatalError("Unable to close connection: " + e.getMessage(), e);
    }
  }

  /**
   * Gets a PreparedStatement object for sending parameterized SQL statements to the database.
   *
   * @param sql a String representing an SQL statement to be sent to the database
   * @return a PreparedStatement object containing the precompiled SQL statement
   */
  @Override
  public PreparedStatement getPreparedStatement(String sql) {
    try {
      return this.connection.prepareStatement(sql);
    } catch (SQLException e) {
      throw new FatalError("Unable to create PreparedStatement: " + e.getMessage(), e);
    }
  }


  /**
   * Closes the provided PreparedStatement object.
   *
   * @param ps the PreparedStatement object to be closed
   */
  @Override
  public void closePreparedStatement(PreparedStatement ps) {
    try {
      if (ps != null) {
        ps.close();
      }
    } catch (SQLException e) {
      throw new FatalError("Unable to close PreparedStatement: " + e.getMessage(), e);
    }
  }
}