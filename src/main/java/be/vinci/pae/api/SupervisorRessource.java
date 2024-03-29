package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.StageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public class SupervisorRessource {

    @POST
    @Path("addSupervisor")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public StageDTO addOneSupervisor( JsonNode json){
        return null;
    }
}
