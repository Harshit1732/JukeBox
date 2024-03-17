package com.crio.jukebox.respositories;

import java.util.List;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.User;

public interface IPlaylistRepository extends CRUDRepository<Playlist, String>  {

    public void deletePlaylist(Playlist playlist);

    public List<Playlist> getAllUserPlaylist(User creator);
    
}
