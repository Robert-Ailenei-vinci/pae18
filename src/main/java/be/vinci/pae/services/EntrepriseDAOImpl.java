package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.exception.EntrepriseNotFoundException;
import be.vinci.pae.exception.FatalError;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an implementation of the {@link EntrepriseDAO} interface.
 */
public class EntrepriseDAOImpl implements EntrepriseDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalBackServices;

  /**
   * Retrieves an enterprise by its identifier from the database.
   *
   * @param id the identifier of the enterprise to retrieve
   * @return the EntrepriseDTO object corresponding to the provided identifier, or null if no
   * enterprise with the given identifier exists
   */
  @Override
  public EntrepriseDTO getOne(int id) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.entreprises WHERE id_entreprise = ?")) {
      preparedStatement.setInt(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          return getEntrepriseMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      throw new EntrepriseNotFoundException("Entreprise not found with id : " + id, e);
    }
    return null;
  }

  /**
   * Retrieves a list of all enterprises from the database.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
  @Override
  public List<EntrepriseDTO> getAll() {
    PreparedStatement getAllUsers = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.entreprises");
    List<EntrepriseDTO> entreprises = new ArrayList<>();
    try (ResultSet rs = getAllUsers.executeQuery()) {
      while (rs.next()) {
        EntrepriseDTO entreprise;
        entreprise = getEntrepriseMethodFromDB(rs);
        entreprises.add(entreprise);
      }
    } catch (Exception e) {
      System.out.println(
          e.getMessage()); // no possible error, only if not connected to db, but not managed here
    }
    return entreprises;
  }

  private EntrepriseDTO getEntrepriseMethodFromDB(ResultSet rs) {
    EntrepriseDTO entreprise = myDomainFactory.getEntreprise();
    try {
      entreprise.setId(rs.getInt("id_entreprise"));
      entreprise.setEmail(rs.getString("email"));
      entreprise.setDesignation(rs.getString("designation"));
      entreprise.setAddress(rs.getString("address"));
      entreprise.setPhoneNumber(rs.getString("phone_num"));
      entreprise.setIsBlacklisted(rs.getBoolean("blacklisted"));
      entreprise.setTradeName(rs.getString("trade_name"));
    } catch (Exception e) {
      throw new FatalError("Error while getting entreprise from db", e);
    }
    return entreprise;
  }
}
