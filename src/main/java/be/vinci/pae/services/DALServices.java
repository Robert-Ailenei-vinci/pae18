package be.vinci.pae.services;

public interface DALServices {
   void startTransaction();
   void commitTransaction();
    void rollbackTransaction();
}
