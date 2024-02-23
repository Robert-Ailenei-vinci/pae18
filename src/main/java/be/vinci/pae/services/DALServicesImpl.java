package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DALServicesImpl is a class that provides methods for managing database connections and prepared
 * statements.
 */
public class DALServicesImpl implements DALServices {

  private final String DATABASEURL =
      "jdbc:postgresql://coursinfo.vinci.be:5432/dblars_hanquet?user=lars_hanquet";
  private final String DATABASEUSER = "lars_hanquet";
  private final String DATABASEPASSWORD = "123456789";
  private Connection connection;

  /**
   * Constructor for DALServicesImpl. It establishes a connection to the database.
   */
  public DALServicesImpl() {
    try {

      this.connection = DriverManager.getConnection(DATABASEURL, DATABASEUSER, DATABASEPASSWORD);
    } catch (SQLException e) {
      System.out.println("Unable to connect to database: " + e.getMessage());
    }
  }

  /**
   * Gets a connection to the database.
   *
   * @return a Connection object representing a connection to the database
   */
  @Override
  public Connection getConnection() {
    return this.connection;
  }

  /**
   * Closes the provided database connection.
   *
   * @param con the Connection object to be closed
   */
  @Override
  public void closeConnection(Connection con) {
    try {
      if (con != null && !con.isClosed()) {
        con.close();
      }
    } catch (SQLException e) {
      System.out.println("Unable to close database connection: " + e.getMessage());
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
      System.out.println("Unable to prepare statement: " + e.getMessage());
      return null;
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
      System.out.println("Unable to close PreparedStatement: " + e.getMessage());
    }
  }
}