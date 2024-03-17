
package com.crio.jukebox.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import com.crio.codingame.entities.ContestStatus;
import com.crio.jukebox.exceptions.SongNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaylistTest {



    @Test
    @DisplayName("add song should add song in song list")

    public void addSong_shouldaddSongInSongList(){

        Song song = new Song("1"," songName", "genre", "albumName", "albumArtist", CommonStatus.NOT_Running);
       
        User creator= new User("name", "1");
        Playlist playlist= new Playlist("1", creator, "playlistName", new ArrayList<>());

        playlist.addSong(song);

        assertEquals(1, playlist.getSongs().size());
    }


   @Test
   @DisplayName("add song should throw Exception Song already present")
    public void addSong_SongAlreadyPresent(){
        Song song = new Song("1"," songName", "genre", "albumName", "albumArtist", CommonStatus.NOT_Running);
       
        User creator= new User("name", "1");
        Playlist playlist= new Playlist("1", creator, "playlistName", new ArrayList<Song>(){
            {
                add(song);
            }
        });

        RuntimeException e= assertThrows(RuntimeException.class, ()->{
            playlist.addSong(song);
        });
        assertEquals("Song already present", e.getMessage() );
    }


    @Test

    @DisplayName("remove song should remove song")
    
    public void removeSong_shouldRemovesongFromsongList(){
        Song song = new Song("1"," songName", "genre", "albumName", "albumArtist", CommonStatus.NOT_Running);
       
        User creator= new User("name", "1");
        Playlist playlist= new Playlist("1", creator, "playlistName", new ArrayList<Song>(){
            {
                add(song);
            }
        });
        playlist.removeSong(song);
        assertEquals(0, playlist.getSongs().size());
    }

    @Test
    @DisplayName("remove song should throw exception Song not found in playlist")
    public void removeSong_shouldThrowSongNotFound(){
        Song song = new Song("1"," songName", "genre", "albumName", "albumArtist", CommonStatus.NOT_Running);
       
        User creator= new User("name", "1");
        Playlist playlist= new Playlist("1", creator, "playlistName", new ArrayList<>());

        SongNotFoundException e= assertThrows(SongNotFoundException.class, ()->{
            playlist.removeSong(song);
        });
        assertEquals("Song not found in playlist", e.getMessage());
    }
    
}
