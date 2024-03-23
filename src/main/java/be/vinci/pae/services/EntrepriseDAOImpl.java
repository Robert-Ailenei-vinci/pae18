package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.exception.EntrepriseNotFoundException;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.utils.LoggerUtil;
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
  private DALServices dalServices;

  /**
   * Retrieves an enterprise by its identifier from the database.
   *
   * @param id the identifier of the enterprise to retrieve
   * @return the EntrepriseDTO object corresponding to the provided identifier, or null if no
   *     enterprise with the given identifier exists
   */
  @Override
  public EntrepriseDTO getOne(int id) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
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
    PreparedStatement getAllUsers = dalServices.getPreparedStatement(
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

  @Override
  public EntrepriseDTO createOne(String tradeName, String designation, String address,
      String phoneNum, String email) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "INSERT INTO pae.entreprises "
            + "(id_entreprise, trade_name, designation, address, phone_num, "
            + "email, blacklisted)"
            + "VALUES (?, ?, ?, ?, ?, ?, false)"
    )) {
      int entrepriseId = nextItemId();
      preparedStatement.setInt(1, entrepriseId);
      preparedStatement.setString(2, tradeName);
      preparedStatement.setString(3, designation);
      preparedStatement.setString(4, address);
      preparedStatement.setString(5, phoneNum);
      preparedStatement.setString(6, email);
      LoggerUtil.logInfo(entrepriseId + tradeName + designation + address + phoneNum + email);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        return getOne(entrepriseId);
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
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

  @Override
  public int nextItemId() {
    String sql = "SELECT MAX(id_contact) FROM pae.contacts";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {
        return rs.getInt(1) + 1;
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return 1;
  }
}
