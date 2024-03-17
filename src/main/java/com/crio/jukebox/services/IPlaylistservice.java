package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;

public interface IPlaylistservice {
    public Playlist createPlaylist(User creator, String name, List<Song> songs);

    public Playlist addSongs(User creator, Playlist playlist, List<Song> songs);

    public Playlist deleteSong(User creator, Playlist playlist, List<Song> songs);

    public void deletePlaylist(User creator, Playlist playlist);

    public Song playPlaylist(User creator, Playlist playlist);


}
