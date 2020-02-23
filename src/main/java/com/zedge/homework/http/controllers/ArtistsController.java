package com.zedge.homework.http.controllers;

import com.zedge.homework.models.Artist;
import com.zedge.homework.services.SearchApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ArtistsController {
    @Autowired
    private SearchApi iTunes;

    @GetMapping("/artists/search")
    @ResponseBody
    public List<Artist> search(@RequestParam(name = "artist", required = false, defaultValue = "aba") String artist)
            throws IOException {

        return iTunes.search(artist);
    }
}
