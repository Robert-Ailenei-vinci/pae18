package be.vinci.pae.services;

import be.vinci.pae.exception.FatalError;
import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DALServicesImpl implements DALBackServices, DALServices{

  private static final String DATA_BASEURL;
  private static final String DATABASE_USER;
  private static final String DATABASE_PASSWORD;

  static {
    DATA_BASEURL = Config.getProperty("DatabaseFilePath");
    DATABASE_USER = Config.getProperty("DataBaseUser");
    DATABASE_PASSWORD = Config.getProperty("DataBasePswd");
  }

  private final ThreadLocal<Connection> threadLocalConnection;
  private final BasicDataSource dataSource;

  public DALServicesImpl() {
    threadLocalConnection = new ThreadLocal<>();
    dataSource = new BasicDataSource();
    dataSource.setUrl(DATA_BASEURL);
    dataSource.setUsername(DATABASE_USER);
    dataSource.setPassword(DATABASE_PASSWORD);
  }

  public Connection getConnection() {
    Connection connection = threadLocalConnection.get();
    if (connection == null) {
      try {
        connection = dataSource.getConnection();
        threadLocalConnection.set(connection);
      } catch (SQLException e) {
        throw new FatalError("Unable to connect to database: " + e.getMessage(), e);
      }
    }
    return connection;
  }

//  public Connection getConnection() {
//    if (connection.get() == null) {
//      try {
//        connection.set(dataSource.getConnection());
//      } catch (SQLException e) {
//        throw new RuntimeException(e);
//      }
//    }
//    return connection.get();
//  }

  public void closeConnection() {
    Connection connection = threadLocalConnection.get();
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new FatalError("Unable to close connection: " + e.getMessage(), e);
    } finally {
      threadLocalConnection.remove();
    }
  }

  @Override
  public PreparedStatement getPreparedStatement(String sql) {
    try {
      return getConnection().prepareStatement(sql);
    } catch (SQLException e) {
      throw new FatalError("Unable to create PreparedStatement: " + e.getMessage(), e);
    }
  }

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

  @Override
  public void startTransaction() {
    try {
      getConnection().setAutoCommit(false);
    } catch (SQLException e) {
      throw new FatalError("Unable to start transaction: " + e.getMessage(), e);
    }
  }

  @Override
  public void commitTransaction() {
    try {
      getConnection().commit();
      getConnection().setAutoCommit(true);
    } catch (SQLException e) {
      throw new FatalError("Unable to commit transaction: " + e.getMessage(), e);
    }
  }

  @Override
  public void rollbackTransaction() {
    try {
      getConnection().rollback();
      getConnection().setAutoCommit(true);
    } catch (SQLException e) {
      throw new FatalError("Unable to rollback transaction: " + e.getMessage(), e);
    }
  }
}