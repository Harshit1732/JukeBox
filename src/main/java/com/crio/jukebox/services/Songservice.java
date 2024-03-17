package com.crio.jukebox.services;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.respositories.ISongRepository;

public class Songservice implements ISongservice {


    private ISongRepository songRepository;

    public Songservice( ISongRepository songRepository)
    {
        this.songRepository= songRepository;
    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> songs= songRepository.findAll();
        return songs;
    }

    @Override
    public Song getSongById(String id) {
        return songRepository.findByid(id);
    }

}
