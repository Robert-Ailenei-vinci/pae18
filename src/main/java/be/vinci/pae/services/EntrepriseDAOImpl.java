package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class represents an implementation of the {@link EntrepriseDAO} interface.
 */
public class EntrepriseDAOImpl implements EntrepriseDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;

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
      System.out.println(e.getMessage());
    }
    return null;
  }

  private EntrepriseDTO getEntrepriseMethodFromDB(ResultSet rs) {
    EntrepriseDTO entreprise = myDomainFactory.getEntreprise();
    try {
      entreprise.setId(rs.getInt("id_user"));
      entreprise.setEmail(rs.getString("email"));
      entreprise.setDesignation(rs.getString("trade_name"));
      entreprise.setAddress(rs.getString("address"));
      entreprise.setPhoneNumber(rs.getString("phone_num"));
      entreprise.setIsBlacklisted(rs.getBoolean("blacklisted"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return entreprise;
  }
}