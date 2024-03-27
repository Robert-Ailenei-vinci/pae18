package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
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
  private DALBackServices dalBackServices;

  /**
   * Retrieves an enterprise by its identifier from the database.
   *
   * @param id the identifier of the enterprise to retrieve
   * @return EntrepriseDTO corresponding to the identifier, or null if no enterprise exists
   */
  @Override
  public EntrepriseDTO getOne(int id) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.entreprises WHERE id_entreprise = ?")) {
      preparedStatement.setInt(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          LoggerUtil.logInfo("entreprise getone with id " + id);

          return getEntrepriseMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
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
        LoggerUtil.logInfo("entreprise getAll");

      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return entreprises;
  }

  @Override
  public EntrepriseDTO createOne(String tradeName, String designation, String address,
      String phoneNum, String email) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
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
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        LoggerUtil.logInfo("enteprise createOne with id " + entrepriseId);
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

  private int nextItemId() {
    String sql = "SELECT MAX(id_contact) FROM pae.contacts";
    try (PreparedStatement stmt = dalBackServices.getPreparedStatement(sql);
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
