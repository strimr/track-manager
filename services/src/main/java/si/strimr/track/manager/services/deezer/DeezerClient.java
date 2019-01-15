package si.strimr.track.manager.services.deezer;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import si.strimr.track.manager.services.deezer.models.SearchResponse;
import si.strimr.track.manager.services.properties.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.Order;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@ApplicationScoped
public class DeezerClient {

    private Logger log = Logger.getLogger(DeezerClient.class.getName());

    @Inject
    private AppProperties appProperties;

    @Inject
    @Metric(name = "deezer_api_call_counter")
    private Counter deezerApiCallCounter;

    private Client httpClient;

    @PostConstruct
    private void init() {
        // https://developers.deezer.com/api/explorer
        httpClient = ClientBuilder.newClient();
    }

    @CircuitBreaker(requestVolumeThreshold = 3)
    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "searchTrackManagerFallback")
    public SearchResponse searchTrackManager(String artist, String track) throws Exception {
        deezerApiCallCounter.inc();

        if(appProperties.isDemoTimeout()) {
            TimeUnit.SECONDS.sleep(4);
        }

        return httpClient
                .target("/search")
                .queryParam("q", String.format("artist:\"%s\" track:\"%s\"", artist, track))
                .request().get(SearchResponse.class);
    }

    public SearchResponse searchTrackManagerFallback(String artist, String track) {

        return new SearchResponse();

    }
}
