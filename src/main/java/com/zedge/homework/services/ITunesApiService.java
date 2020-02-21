package com.zedge.homework.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.zedge.homework.models.Album;
import com.zedge.homework.models.Artist;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ITunesApiService implements SearchApi {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private ObjectMapper jackson = new ObjectMapper();

    @Override
    public List<Artist> search(String term) throws IOException {
        HttpRequest request = makeGetRequest("https://itunes.apple.com/search?entity=allArtist&term=" + term);
        String jsonString = callRequest(request);

        return convertValue(jackson.readTree(jsonString).findValue("results"), Artist.class);
    }

    @Override
    public List<Album> getTop5Albums(int amgArtistId) throws IOException  {
        HttpRequest request = makeGetRequest("https://itunes.apple.com/lookup?amgArtistId=" + amgArtistId + "&entity=album&limit=5");
        String jsonString = callRequest(request);

        return convertValue(getFilteredAlbumsJson(jsonString), Album.class);
    }

    public <T> List<T> convertValue(Object fromValue, Class<T> tClass) {
        CollectionType listType = jackson.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        return jackson.convertValue(fromValue, listType);
    }

    public ArrayNode getFilteredAlbumsJson(String jsonString) {
        ArrayNode filtered = JsonNodeFactory.instance.arrayNode();
        try {
            JsonNode jsonNode = jackson.readTree(jsonString);
            ArrayNode results = (ArrayNode) jsonNode.findValue("results");
            for (int i = 0; i < results.size(); i++) {
                String compare = results.get(i).findValue("wrapperType").toString();
                if (compare.equalsIgnoreCase("\"collection\"")) {
                    filtered.add(results.get(i));
                }
            }
        } catch (Exception e) {
            // handle or throw exception
        }

        return filtered;
    }

    private HttpRequest makeGetRequest(String uri) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Accept", "application/json")
                .GET()
                .build();
    }

    private String callRequest(HttpRequest request) {
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .get();
        } catch (Exception e) {
            // handle or throw exception
        }

        return "'{}'";
    }
}