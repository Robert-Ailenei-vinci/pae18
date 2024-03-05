package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SchoolYearDTO;

/**
 * The interface SchoolYearUCC represents the controller for managing {@link SchoolYearDTO}
 * objects.
 */
public interface SchoolYearUCC {

    /**
     * Retrieves a {@link SchoolYearDTO} object by its identifier.
     *
     * @param schoolYearId the identifier of the school year to retrieve
     * @return the {@link SchoolYearDTO} object corresponding to the provided identifier, or null if
     * no school year with the given identifier exists
     */
    SchoolYearDTO getOne(int schoolYearId);
}
