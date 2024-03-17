package com.crio.jukebox.services;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Song;

public interface ISongservice {
    
    public List<Song> getAllSongs();

    public Song getSongById(String id);

}
