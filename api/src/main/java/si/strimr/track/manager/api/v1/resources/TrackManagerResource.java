package si.strimr.track.manager.api.v1.resources;

//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import si.strimr.track.manager.services.TrackManagerBean;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Log(value = LogParams.METRICS, methodCall = true)
@ApplicationScoped
@Path("/track-manager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrackManagerResource {

    @Inject
    private TrackManagerBean trackManagerBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("/filtered")
    public Response getTrackManagerFiltered() {

        List<si.strimr.track.manager.models.dtos.TrackMetadata> trackManager;

        trackManager = trackManagerBean.getTrackManagerFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(trackManager).build();
    }
}
