/**
 * The DALServices interface provides methods for managing database transactions.
 * These methods are used to start, commit, and rollback a transaction.
 */
package be.vinci.pae.services;

public interface DALServices {

  /**
   * Starts a new database transaction.
   * This method should be called before executing any database operation that needs to be part of a transaction.
   */
  void startTransaction();

  /**
   * Commits the current database transaction.
   * This method should be called after all database operations of a transaction have been successfully executed.
   */
  void commitTransaction();

  /**
   * Rolls back the current database transaction.
   * This method should be called if an error occurs during the execution of a database operation that is part of a transaction.
   */
  void rollbackTransaction();
}