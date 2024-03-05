package be.vinci.pae.business.domain;

/**
 * This interface represents a data transfer object for a school year.
 */
public interface SchoolYearDTO {

  /**
   * Retrieves the identifier of the school year.
   *
   * @return the identifier of the school year
   */
  int getId();

  /**
   * Sets the identifier of the school year.
   *
   * @param id the identifier to set for the school year
   */
  void setId(int id);

  /**
   * Retrieves the format of the school year.
   *
   * @return the format of the school year
   */
  String getYearFormat();

  /**
   * Sets the format of the school year.
   *
   * @param yearFormat the format to set for the school year
   */
  void setYearFormat(String yearFormat);
}
