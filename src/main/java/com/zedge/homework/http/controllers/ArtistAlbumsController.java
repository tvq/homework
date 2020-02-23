package com.zedge.homework.http.controllers;

import com.zedge.homework.models.Album;
import com.zedge.homework.services.SearchApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ArtistAlbumsController {
    @Autowired
    private SearchApi iTunes;

    @GetMapping("/artists/{amgArtistId}/albums/top5")
    @ResponseBody
    public List<Album> show(@PathVariable int amgArtistId) throws IOException {
        return iTunes.getTop5Albums(amgArtistId);
    }
}
