package be.vinci.pae.services;

/**
 * The DALServices interface provides methods for managing database transactions.
 * It provides methods to start, commit, and rollback a transaction.
 * These methods should be used to ensure that a group of database
 *    operations are executed as a single unit of work.
 * If any operation within the transaction fails,
 *    the transaction can be rolled back
 *    to undo the operations that were executed before the failure.
 * If all operations within the transaction are successful,
 *    the transaction can be committed to permanently apply the changes
 *    to the database.
 */
public interface DALServices {

  /**
   * Starts a new database transaction.
   * This method should be called before executing any database operation
   *    that needs to be part of a transaction.
   */
  void startTransaction();

  /**
   * Commits the current database transaction.
   * This method should be called after all database operations
   *    of a transaction have been successfully executed.
   */
  void commitTransaction();

  /**
   * Rolls back the current database transaction.
   * This method should be called if an error occurs
   *    during the execution of a database operation that is part of a transaction.
   */
  void rollbackTransaction();
}