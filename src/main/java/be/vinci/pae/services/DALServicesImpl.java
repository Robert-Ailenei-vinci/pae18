package be.vinci.pae.services;

import be.vinci.pae.exception.FatalError;
import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * This class represents an implementation of the {@link DALServices} and {@link DALBackServices}
 * interfaces.
 */
public class DALServicesImpl implements DALBackServices, DALServices {

  private static final String DATA_BASEURL;
  private static final String DATABASE_USER;
  private static final String DATABASE_PASSWORD;
  private static final int MAX_CONNECTION;


  static {
    DATA_BASEURL = Config.getProperty("DatabaseFilePath");
    DATABASE_USER = Config.getProperty("DataBaseUser");
    DATABASE_PASSWORD = Config.getProperty("DataBasePswd");
    MAX_CONNECTION = Config.getIntProperty("MaxConnections");
  }

  private final ThreadLocal<Connection> threadLocalConnection;
  private final BasicDataSource dataSource;

  private final ThreadLocal<Integer> transactionCounter;

  /**
   * Increase the transactions count.
   */
  private void increaseTransactionCounter() {
    transactionCounter.set(transactionCounter.get() + 1);
  }


  /**
   * Decrease le compteur de transactions.
   */
  private void decreaseTransactionCounter() {
    transactionCounter.set(transactionCounter.get() - 1);
  }


  /**
   * Get the count of transaction.
   *
   * @return number of transaction going on.
   */

  private int getTransactionCount() {
    return transactionCounter.get();
  }

  /**
   * Constructs a new DALServicesImpl instance. Initializes the ThreadLocal for connection and sets
   * up the BasicDataSource with the database URL, username, and password. The database
   * configuration is retrieved from the Config class.
   */
  public DALServicesImpl() {
    threadLocalConnection = new ThreadLocal<>();
    dataSource = new BasicDataSource();
    dataSource.setMaxTotal(MAX_CONNECTION);
    dataSource.setUrl(DATA_BASEURL);
    dataSource.setUsername(DATABASE_USER);
    dataSource.setPassword(DATABASE_PASSWORD);

    transactionCounter = ThreadLocal.withInitial(() -> 0); // Initialiser le compteur à 0 pour chaque thread

  }

  private Connection getConnection() {
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

  private void closeConnection() {
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

    if (getTransactionCount() > 0) {
      // if not first transaction return
      increaseTransactionCounter();
      return;
    }

    try {
      getConnection().setAutoCommit(false);
      increaseTransactionCounter();
    } catch (SQLException e) {
      throw new FatalError("Unable to start transaction: " + e.getMessage(), e);
    }
  }

  @Override
  public void commitTransaction() {

    if (getTransactionCount() > 1) {
      // if not last commit return
      decreaseTransactionCounter();
      return;
    }

    try {
      getConnection().commit();
      getConnection().setAutoCommit(true);
      decreaseTransactionCounter();

    } catch (SQLException e) {
      throw new FatalError("Unable to commit transaction: " + e.getMessage(), e);
    } finally {
      closeConnection();
    }
  }

  @Override
  public void rollbackTransaction() {
    try {
      if (getTransactionCount()>1) {
        // if not last commit return
        decreaseTransactionCounter();
        return;
      }

      getConnection().rollback();
      getConnection().setAutoCommit(true);
      decreaseTransactionCounter();

    } catch (SQLException e) {
      throw new FatalError("Unable to rollback transaction: " + e.getMessage(), e);
    } finally {
      closeConnection();
    }
  }
}