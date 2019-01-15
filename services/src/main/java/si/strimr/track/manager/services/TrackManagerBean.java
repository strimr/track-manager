package si.strimr.track.manager.services;

import si.strimr.track.manager.models.dtos.TrackMetadata;
import si.strimr.track.manager.services.clients.TrackMetadataClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


@RequestScoped
public class TrackManagerBean {

    private Logger log = Logger.getLogger(TrackManagerBean.class.getName());

    @Inject
    private TrackMetadataClient trackMetadataClient;

    public List<TrackMetadata> getTrackManagerFilter(UriInfo uriInfo) {
        return trackMetadataClient.metadataFilter(uriInfo);
    }
}
