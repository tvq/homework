package com.zedge.homework.services;

import com.zedge.homework.models.Album;
import com.zedge.homework.models.Artist;

import java.io.IOException;
import java.util.List;

public interface SearchApi {
    public List<Artist> search(String term) throws IOException;
    public List<Album> getTop5Albums(int amgArtistId) throws IOException;
}
