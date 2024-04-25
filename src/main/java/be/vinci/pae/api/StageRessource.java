package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.StageUCC;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.LoggerUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

/**
 * Represents a RESTful API resource for managing stages.
 */
@Singleton
@Path("/stages")
public class StageRessource {

  @Inject
  private StageUCC stageUCC;

  /**
   * Retrieves a stage by the authenticated user's ID.
   *
   * @param requestContext The context of the current request.
   * @return The {@link StageDTO} representing the stage for the authenticated user, or {@code null}
   *     if not found.
   */
  @GET
  @Path("stageByUserId")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public StageDTO getOneStageByUserId(@Context ContainerRequestContext requestContext) {
    LoggerUtil.logInfo("Starting: stages/getAllByUserId");
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    int userId = authenticatedUser.getId();
    StageDTO stage = stageUCC.getOneStageByUserId(userId);
    if (stage != null) {
      LoggerUtil.logInfo("GetStageByUserId successful");
    }
    return stage;
  }

  /**
   * Retrieves a stage by a specific user ID.
   *
   * @param userId The ID of the user whose stage is to be retrieved.
   * @return The {@link StageDTO} representing the stage for the specified user, or {@code null} if
   *     not found.
   */
  @GET
  @Path("stageByUserId/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public StageDTO getOneStageByUserId(@PathParam("userId") int userId) {
    LoggerUtil.logInfo("Starting: stages/getAllByUserId");
    StageDTO stage = stageUCC.getOneStageByUserId(userId);
    if (stage != null) {
      LoggerUtil.logInfo("GetStageByUserId successful");
    }
    return stage;
  }

  /**
   * Modifies the internship subject for a stage.
   *
   * @param json           The JSON node containing modified data for the stage.
   * @param requestContext The context of the current request.
   * @return The modified {@link StageDTO} representing the updated stage.
   */
  @PUT
  @Path("modifyStage")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public StageDTO modifyStage(JsonNode json, @Context ContainerRequestContext requestContext) {
    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    int userId = authenticatedUser.getId();
    String subject = json.get("internship_project").asText();
    int contactId = json.get("id_contact").asInt();
    int version = json.get("version").asInt();
    StageDTO stage = stageUCC.modifyStage(userId, subject, contactId, version);
    if (stage != null) {
      LoggerUtil.logInfo("ModifyStage successful");
    }
    return stage;
  }
}
