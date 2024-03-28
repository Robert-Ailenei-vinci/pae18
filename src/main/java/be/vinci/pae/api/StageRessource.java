package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.StageUCC;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
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
   * Get one stage by user id.
   *
   * @param requestContext the request context
   * @return the stage DTO
   */
  @GET
  @Path("stageByUserId")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public StageDTO getOneStageByUserId(@Context ContainerRequestContext requestContext) {
    UserDTO authentifiedUser = (UserDTO) requestContext.getProperty("user");
    int userId = authentifiedUser.getId();
    StageDTO toReturn = stageUCC.getOneStageByUserId(userId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetStageByUserId successful");
    }
    return toReturn;
  }
}
