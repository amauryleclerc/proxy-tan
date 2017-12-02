package fr.aleclerc.proxy.tan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopController.class);

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "stops", produces = "application/json; charset=ISO-8859-1")
    public String stops(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        Map<String, List<String>> map = allRequestParams.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Collections.singletonList(e.getValue())));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://open.tan.fr/ewp/arrets.json")
                .queryParams(CollectionUtils.toMultiValueMap(map))//
                .build()
                .toUri();
        LOGGER.info("request : {}", uri);
        return restTemplate.getForObject(uri, String.class);
    }

    @RequestMapping(value = "stops/{lat}/{lon}", produces = "application/json; charset=ISO-8859-1")
    public String stops(@RequestParam Map<String, String> allRequestParams, ModelMap model, @PathVariable("lat") String lat, @PathVariable("lon") String lon) {
        Map<String, List<String>> map = allRequestParams.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Collections.singletonList(e.getValue())));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://open.tan.fr/ewp/arrets.json")
                .pathSegment(lat, lon)//
                .queryParams(CollectionUtils.toMultiValueMap(map))//
                .build()
                .toUri();
        LOGGER.info("request : {}", uri);
        return restTemplate.getForObject(uri, String.class);
    }

    @RequestMapping(value = "stop/{id}", produces = "application/json; charset=ISO-8859-1")
    public String stop(@RequestParam Map<String, String> allRequestParams, ModelMap model, @PathVariable("id") String id) {
        Map<String, List<String>> map = allRequestParams.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Collections.singletonList(e.getValue())));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://open.tan.fr/ewp/tempsattente.json")
                .pathSegment(id)//
                .queryParams(CollectionUtils.toMultiValueMap(map))//
                .build()
                .toUri();
        LOGGER.info("request : {}", uri);
        return restTemplate.getForObject(uri, String.class);
    }

    @RequestMapping(value = "schedule/{id}/{line}/{direction}", produces = "application/json; charset=ISO-8859-1")
    public String schedule(@RequestParam Map<String, String> allRequestParams, ModelMap model, @PathVariable("id") String id, @PathVariable("line") String line, @PathVariable("direction") String direction) {
        Map<String, List<String>> map = allRequestParams.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Collections.singletonList(e.getValue())));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://open.tan.fr/ewp/horairesarret.json")
                .pathSegment(id, line, direction)//
                .queryParams(CollectionUtils.toMultiValueMap(map))//
                .build()
                .toUri();
        LOGGER.info("request : {}", uri);
        return restTemplate.getForObject(uri, String.class);
    }


}
