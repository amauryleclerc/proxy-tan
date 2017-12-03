package fr.aleclerc.proxy.tan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopController.class);

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    @CrossOrigin
    @RequestMapping(value = "stops", produces = "application/json; charset=UTF-8")
    public String stops(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        Map<String, List<String>> map = allRequestParams.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Collections.singletonList(e.getValue())));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://open.tan.fr/ewp/arrets.json")
                .queryParams(CollectionUtils.toMultiValueMap(map))//
                .build()
                .toUri();
        LOGGER.info("request : {}", uri);
        return restTemplate.getForObject(uri, String.class);
    }

    @CrossOrigin
    @RequestMapping(value = "stops/{lat}/{lon}", produces = "application/json; charset=UTF-8")
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

    @CrossOrigin
    @RequestMapping(value = "stop/{id}", produces = "application/json; charset=UTF-8")
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

    @CrossOrigin
    @RequestMapping(value = "schedule/{id}/{line}/{direction}", produces = "application/json; charset=UTF-8")
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
