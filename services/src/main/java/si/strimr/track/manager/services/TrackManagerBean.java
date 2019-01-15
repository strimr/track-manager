package si.strimr.track.manager.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.strimr.track.manager.services.deezer.DeezerClient;
import si.strimr.track.manager.services.deezer.models.SearchResponse;
import si.strimr.track.manager.services.deezer.models.SearchResponseData;
import si.strimr.track.manager.services.properties.AppProperties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RequestScoped
public class TrackManagerBean {

    private Logger log = Logger.getLogger(TrackManagerBean.class.getName());

    @Inject
    private AppProperties appProperties;

    public List<si.strimr.track.manager.models.dtos.TrackMetadata> getTrackManagerFilter(UriInfo uriInfo) {
        // TODO - Call metadata api
        return Collections.emptyList();
    }
}
