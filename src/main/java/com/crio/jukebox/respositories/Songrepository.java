package com.crio.jukebox.respositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.Song;

public class Songrepository implements ISongRepository{

    private Map<String, Song> songMap;
    private Integer autoincrement=0;


    public Songrepository(){
        
        this.songMap= new HashMap<>();
    }
   
    @Override
    public Song save(Song entity) {
        String id= entity.getId();
        if(entity.getId()==null)
        {
           autoincrement++;
           Song song= new Song(Integer.toString(autoincrement), entity.getName(), entity.getGenre(), entity.getAlbumName(), entity.getAlbumArtist(),CommonStatus.NOT_Running, entity.getArtistList());
           songMap.put(Integer.toString(autoincrement),song);
           return song;
        }
        songMap.put(id,entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
       List<Song> songs= songMap.entrySet().stream().map(Map.Entry:: getValue).collect(Collectors.toList());
       return songs;
    }

    @Override
    public Song findByid(String id) {
        return songMap.get(id);
     }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Optional<Song> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
