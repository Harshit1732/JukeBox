package com.crio.jukebox.respositories;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.User;

public class Playlistrepository implements IPlaylistRepository {

    private final Map<String, Playlist> playlistMap;
    private Integer autoincrement=0;

    public Playlistrepository(){
        playlistMap= new HashMap<>();
    }

    public Playlistrepository(Map<String, Playlist> playlistMap)
    {
        this.playlistMap= playlistMap;
        this.autoincrement= playlistMap.size();

    }

    
    @Override
    public Playlist save(Playlist entity) {

        if(entity.getId() == null)
        {
           autoincrement++;
           Playlist playlist= new Playlist(Integer.toString(autoincrement), entity.getcreator(),entity.getName(), entity.getSongs());
           playlistMap.put(Integer.toString(autoincrement),playlist);
           return playlist;
        }
        playlistMap.put(entity.getId(),entity);
        return entity;
     
    }

    @Override
    public List<Playlist> findAll() {
        List<Playlist> playlists= playlistMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        return playlists;
    }

    @Override
    public Optional<Playlist> findById(String id) {
        return Optional.ofNullable(playlistMap.get(id));
    }

    @Override
    public void deleteById(String id) {
      //  //
        
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        playlistMap.remove(playlist.getId());
    }

    @Override
    public List<Playlist> getAllUserPlaylist(User creator) {
        List<Playlist> playlists= playlistMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        List<Playlist> userPlaylist= playlists.stream().filter(e->e.getcreator().getId().equals(creator.getId())).collect(Collectors.toList());
        return userPlaylist;
    }
    

}
