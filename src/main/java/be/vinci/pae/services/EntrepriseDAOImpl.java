package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.OptimisticLockException;
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
  @Inject
  private SchoolYearDAO schoolYearDAO;

  /**
   * Retrieves an enterprise by its identifier from the database.
   *
   * @param id the identifier of the enterprise to retrieve
   * @return EntrepriseDTO corresponding to the identifier, or null if no enterprise exists
   */
  @Override
  public EntrepriseDTO getOne(int id) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
      "SELECT e.id_entreprise, e.trade_name, e.designation, e.address , e.phone_num, e.email, e.blacklisted, e.reason_blacklist, e._version FROM pae.entreprises e WHERE e.id_entreprise = ?")) {
      preparedStatement.setInt(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          LoggerUtil.logInfo("entreprise getone with id " + id);

          EntrepriseDTO entreprise = getEntrepriseMethodFromDB(rs);
          entreprise.setVersion(rs.getInt("_version")); // Set the version
          return entreprise;
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
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
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    LoggerUtil.logInfo("entreprise getAll");
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
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  /**
   * Blacklists an enterprise.
   *
   * @param entreprise
   * @return the newly updated entreprise and it s blacklistred if no errors prior to that
   */
  @Override
  public EntrepriseDTO blacklist(EntrepriseDTO entreprise, int version) {
    int lastVersion = getLastVersionFromDB(entreprise.getId());

    if (lastVersion != version) {
      throw new OptimisticLockException("Optimisitc lock exception");
    }

    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
      "UPDATE pae.entreprises SET blacklisted = true , reason_blacklist = ? , _version= _version+1 WHERE id_entreprise = ? AND _version = ?")) {
      preparedStatement.setString(1, entreprise.getBlacklistReason());
      preparedStatement.setInt(2, entreprise.getId());
      preparedStatement.setInt(3, version);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        LoggerUtil.logInfo("entreprise blacklist with id " + entreprise.getId());
        return getOne(entreprise.getId());
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  /**
   * Unblacklists an entreprise.
   *
   * @param entreprise the entreprise to unblacklist
   * @return the newly updated entreprise and it s blacklistred if no errors prior to that
   */
  @Override
  public EntrepriseDTO unblacklist(EntrepriseDTO entreprise, int version) {
    int lastVersion = getLastVersionFromDB(entreprise.getId());

    if (lastVersion != version) {
      throw new OptimisticLockException("Optimisitc lock exception");
    }

    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
      "UPDATE pae.entreprises SET blacklisted = false , reason_blacklist = null ,_version = _version + 1 WHERE id_entreprise = ? AND _version = ?")) {
      preparedStatement.setInt(1, entreprise.getId());
      preparedStatement.setInt(2, version);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        LoggerUtil.logInfo("entreprise unblacklist with id " + entreprise.getId());
        return getOne(entreprise.getId());
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  @Override
  public List<EntrepriseDTO> getAllForSchoolYear(int idSchoolYear) {
    PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
      "SELECT DISTINCT e.* FROM pae.entreprises e " +
        "JOIN pae.contacts c ON e.id_entreprise = c.entreprise " +
        "WHERE c.school_year = ?");
    List<EntrepriseDTO> entreprises = new ArrayList<>();
    try {
      preparedStatement.setInt(1, idSchoolYear);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          EntrepriseDTO entreprise = getEntrepriseMethodFromDB(rs);
          entreprises.add(entreprise);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
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
      entreprise.setBlacklistReason(rs.getString("reason_blacklist"));
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error while getting entreprise from db", e);
    }
    return entreprise;
  }

  @Override
  public int getNbStagesForCurrentYear(int entrepriseId) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
      "SELECT COUNT(*) FROM pae.entreprises e " +
        "JOIN pae.contacts c ON e.id_entreprise = c.entreprise " +
        "WHERE c.school_year = ? AND e.id_entreprise = ?")) {
      preparedStatement.setInt(1, schoolYearDAO.getCurrentSchoolYear().getId());
      preparedStatement.setInt(2, entrepriseId); // Set the entreprise id
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return 0;
  }

  private int nextItemId() {
    String sql = "SELECT MAX(id_contact) FROM pae.contacts";
    try (PreparedStatement stmt = dalBackServices.getPreparedStatement(sql);
      ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {
        return rs.getInt(1) + 1;
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return 1;
  }

  private int getLastVersionFromDB(int entrepriseId) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
      "SELECT _version FROM pae.entreprises WHERE id_entreprise = ? ")) {
      preparedStatement.setInt(1, entrepriseId);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("_version");
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error fetching last version", e);
      throw new FatalError("Erreur lors de la récupération de la dernière version");
    }
    return 0;

  }
}
