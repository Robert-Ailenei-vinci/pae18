package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.SupervisorUCC;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.AuthorisationException;
import be.vinci.pae.utils.LoggerUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

public class SupervisorRessource {

    @Inject
    EntrepriseUCC myEntreprise;
    @Inject
    UserUCC myUserUCC;
    @Inject
    SupervisorUCC mySupervisorUCC;


    /**
     * Adds a new stage supervisor. This method is accessed via HTTP POST request to the path
     * "/supervisor/addSupervisor". It expects a JSON object containing the fields: first_name, last_name,
     * entreprise, phone_number, email. It returns the added supervisor in JSON format. Requires
     * authorization.
     *
     * @param requestContext The context of the HTTP request.
     * @param json           The JSON object containing enterprise details.
     * @return The {@link SupervisorDTO} representing the added supervisor.
     * @throws be.vinci.pae.exception.BadRequestException    If any required field is missing in the JSON object.
     * @throws AuthorisationException If the user is not recognized.
     */
    @POST
    @Path("addSupervisor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public SupervisorDTO addOneSupervisor(@Context ContainerRequestContext requestContext, JsonNode json) throws BadRequestException, AuthorisationException {
        if (!json.hasNonNull("first_name")
                || !json.hasNonNull("last_name") || !json.hasNonNull("entreprise") || !json.hasNonNull("phone_number")) {
            LoggerUtil.logError("All fields required to create an enterprise.",
                    new be.vinci.pae.exception.BadRequestException(""));
            throw new be.vinci.pae.exception.BadRequestException("All fields required to create an enterprise.");
        }
        EntrepriseDTO entrepriseD = (EntrepriseDTO) requestContext.getProperty("entreprise");
        int entrepriseId = entrepriseD.getId();
        String firstName = json.get("first_name").asText();
        String lastName = json.get("last_name").asText();
        String phoneNumber = json.get("phone_number").asText();
        String email = json.get("email").asText();

        EntrepriseDTO entrepriseDTO = myEntreprise.getOne(entrepriseId);
        if (entrepriseDTO == null) {
            LoggerUtil.logError("Entreprise not recognised", new AuthorisationException(""));
            throw new AuthorisationException("Entreprise not recognised");
        }

        SupervisorDTO supervisorDTO = mySupervisorUCC.createOne(lastName, firstName, entrepriseDTO, phoneNumber, email);
        if (supervisorDTO == null) {
            LoggerUtil.logError("Supervisor not created", new be.vinci.pae.exception.BadRequestException(""));
            throw new be.vinci.pae.exception.BadRequestException("Supervisor not created");
        }
        LoggerUtil.logInfo("addOne successful");
        return supervisorDTO;
    }
}
