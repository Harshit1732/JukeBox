package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;


public interface IUserservice {

    public User createUser(String name);
    
    public List<User> getAllUsers();
   
    // public void choosePlaylist(User creator, Playlist playlist);

    public Song playSong(User creator, Song song);

    public Song switchToPreviousSong(User creator, String Back);

    public Song switchToNextSong(User creator, String Next);

}
