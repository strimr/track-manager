package si.strimr.track.manager.services.clients;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import si.strimr.track.manager.models.dtos.TrackMetadata;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class TrackMetadataClient {

    private Logger log = Logger.getLogger(TrackMetadataClient.class.getName());

    private Client httpClient;

    @Inject
    @DiscoverService("rso-track-metadata")
    private Optional<String> trackMetadataBaseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    @CircuitBreaker(requestVolumeThreshold = 3)
    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "metadataFilterFallback")
    public List<TrackMetadata> metadataFilter(UriInfo uriInfo) {

        if (trackMetadataBaseUrl.isPresent()) {
            try {
                // TODO - pass uriInfo
                return httpClient
                    .target(trackMetadataBaseUrl.get() + "/v1/track-metadata/filtered")
                    .request().get(new GenericType<List<TrackMetadata>>(){});
            } catch (Exception e) {
                log.severe(e.getMessage());
                throw new InternalServerErrorException(e);
            }
        }

        return Collections.emptyList();

    }

    public List<TrackMetadata> metadataFilterFallback(UriInfo uriInfo) {

        return Collections.emptyList();

    }
}
