package com.crio.jukebox.services;

import net.bytebuddy.implementation.bind.annotation.Empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.NoSuchUserFoundException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.respositories.IPlaylistRepository;
import com.crio.jukebox.respositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("PlaylistserviceTest") // Corrected the display name
@ExtendWith(MockitoExtension.class)
public class PlaylistserviceTest {



    @Mock
    private IPlaylistRepository iPlaylistRepository;

    @Mock
    private IUserRepository iUserRepository;


    @InjectMocks
    private Playlistservice playlistservice;


    @Test
    @DisplayName("create playlist should create playlist")

    public void createPlaylist_shouldCreatePlaylist(){

        List<Song> songs= new ArrayList<Song>(){
            {
                add(new Song("1", "South of the Border","Pop","No.6 Collaborations Project","Ed Sheeran",CommonStatus.NOT_Running));
                add(new Song("2", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran",CommonStatus.NOT_Running));
            }
        };
        User creator= new User("1","kiran");
        Playlist expected= new Playlist( "1", creator,"playlist-1",Collections.emptyList());

        when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(iPlaylistRepository.save(any(Playlist.class))).thenReturn(expected);

        Playlist actual= playlistservice.createPlaylist(creator,"playlist 1", songs);

        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("createPlaylist should throw User not found exception")
    
    public void createPlaylist_shouldThrowUserNotfoundException(){
        String playlistName = "Test Playlist";
        List<Song> songs = new ArrayList<>();
        User creator = new User("kiran", "1");

        when(iUserRepository.findById(anyString())).thenReturn(Optional.empty());
    
       NoSuchUserFoundException exception= assertThrows(NoSuchUserFoundException.class, () -> {
            playlistservice.createPlaylist(creator, playlistName, songs);
        });

        assertEquals("user is not found", exception.getMessage());

        verify(iPlaylistRepository, never()).save(any(Playlist.class));

    }


    @Test
    @DisplayName("add song should add song in playlist")

    public void addSongToPlaylist_shouldAddSong(){

        List<Song> songs= new ArrayList<Song>(){
            {
            add(new Song("3", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran",CommonStatus.NOT_Running));
            }
        };
        User creator= new User("kiran", "1");
        Playlist existingPlaylist= new Playlist("1", creator, "playlist-1", songs);

        // when(iUserRepository.save(any(User.class))).thenReturn(creator);
        when(iPlaylistRepository.save(any(Playlist.class))).thenReturn(existingPlaylist);
        when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(existingPlaylist));
      
    List<Song> song= new ArrayList<Song>(){
        {
        add(new Song("2", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
        add(new Song("4", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
        }
    };
        Playlist updatedPlaylist= playlistservice.addSongs(creator, existingPlaylist, song);
        assertEquals(3, updatedPlaylist.getSongs().size());
        assertTrue(updatedPlaylist.getSongs().containsAll(song));
       
    }

    @Test
    @DisplayName("add song to play list should throw playlist not found exception")

    public void addSongToPlaylist_shouldThrowPlaylistNotFound(){

        User creator= new User("kiran", "1");
        Playlist existingPlaylist= new Playlist("1", creator, "playlist-1", Collections.emptyList());

        // when(iUserRepository.save(any(User.class))).thenReturn(creator);
      
        when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn( new ArrayList<>());
      
        PlaylistNotFoundException exception= assertThrows(PlaylistNotFoundException.class,()->{
            playlistservice.addSongs(creator, existingPlaylist, Collections.emptyList());
        });

        assertEquals("Playlist not found", exception.getMessage());
        verify(iPlaylistRepository, never()).save(any(Playlist.class));

    }


     @Test
     @DisplayName("add song to playlist should throw Song not found exception")
    public void addSongToPlaylist_shouldThrowSongNotFound(){

        List<Song> songs= new ArrayList<Song>(){
            {
            add(new Song("3", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran",CommonStatus.NOT_Running));
            }
        };
        User creator= new User("kiran", "1");
        Playlist existingPlaylist= new Playlist("1", creator, "playlist-1", songs);

        // when(iUserRepository.save(any(User.class))).thenReturn(creator);
        when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(existingPlaylist));
      
        SongNotFoundException exception= assertThrows(SongNotFoundException.class,()->{
            playlistservice.addSongs(creator, existingPlaylist, Collections.emptyList());
        });

        assertEquals("Song not found", exception.getMessage());
        verify(iPlaylistRepository, never()).save(any(Playlist.class));
    }

   
    @Test
    @DisplayName("delete song should delete song in playlist")

    public void deleteSongToPlaylist_shouldDeleteSong(){

        List<Song> songs= new ArrayList<Song>(){
            {
            add(new Song("1", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
            add(new Song("2", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
            }
        };
        User creator= new User("kiran", "1");
        Playlist existingPlaylist= new Playlist("1", creator, "playlist-1", songs);

        when(iPlaylistRepository.save(any(Playlist.class))).thenReturn(existingPlaylist);
        when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(existingPlaylist));

        List<Song> song= new ArrayList<Song>(){
            {
             add(new Song("1", "Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
            }
        };
        Playlist updatedPlaylist= playlistservice.deleteSong(creator, existingPlaylist, song);
        assertEquals(1, updatedPlaylist.getSongs().size());
        assertFalse(updatedPlaylist.getSongs().containsAll(song));
    }

 

   @Test
   @DisplayName("delete song to playlist should throw playlist not found")
    public void deleteSongToPlaylist_shouldThrowPlaylistNotFound(){

        User creator= new User("kiran", "1");
        Playlist existingPlaylist= new Playlist("1", creator, "playlist-1", Collections.emptyList());

        when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(new ArrayList<>());

           
        PlaylistNotFoundException exception= assertThrows(PlaylistNotFoundException.class,()->{
            Playlist updatedPlaylist= playlistservice.deleteSong(creator, existingPlaylist, Collections.emptyList());
        });

        assertEquals("Playlist not found", exception.getMessage());
        verify(iPlaylistRepository, never()).save(any(Playlist.class));
    }


    @Test
    @DisplayName("delete playlist should delete a playlist")

    public void deletePlaylist_shouldDeleteplaylist(){
        List<Song> songs = new ArrayList<>(Arrays.asList(
            new Song("3", "Remember the Name", "Pop", "No.6 Collaborations Project", "Ed Sheeran", CommonStatus.NOT_Running)));

        User creator = new User("kiran", "1");
        Playlist existingPlaylist = new Playlist("1", creator, "playlist-1", songs);

      
        when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(existingPlaylist));
        doNothing().when(iPlaylistRepository).deletePlaylist(any(Playlist.class));

        playlistservice.deletePlaylist(creator, existingPlaylist);

        verify(iPlaylistRepository, times(1)).deletePlaylist(existingPlaylist);
  }

  @Test

  @DisplayName("delete playlist should throw playlist not found")

  public void deletePlaylist_shouldThrowPlaylistNotFound(){

    User creator = new User("kiran", "1");
    Playlist existingPlaylist = new Playlist("1", creator, "playlist-1", Collections.emptyList());

    when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
    when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(new ArrayList<>());

    PlaylistNotFoundException exception= assertThrows(PlaylistNotFoundException.class,()->{
        playlistservice.deletePlaylist(creator, existingPlaylist);
    });

    assertEquals("Playlist not found", exception.getMessage());

    verify(iPlaylistRepository, never()).deletePlaylist(existingPlaylist);
  }


  @Test
  @DisplayName("playPlaylist should play first song")

  public void playPlaylist_shouldPlayFirstSong(){

    List<Song> songs = new ArrayList<>(Arrays.asList(
        new Song("3", "Remember the Name", "Pop", "No.6 Collaborations Project", "Ed Sheeran", CommonStatus.NOT_Running)));


    User creator = new User("kiran", "1");
    Playlist playlist = new Playlist("1", creator, "playlist-1", songs);


    when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
    when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(playlist));


    Song playedSong = playlistservice.playPlaylist(creator, playlist);
    assertEquals(CommonStatus.Running, playedSong.getSongStatus());
    // verify(iPlaylistRepository, times(1)).save(playlist);
  }


  @Test
  @DisplayName("playPlaylist with No Song should throw Song Not Found")

  public void playPlaylist_withNoSong_shouldThrowSongNotFound(){

    User creator = new User("kiran", "1");
    Playlist playlist = new Playlist("1", creator, "playlist-1", Collections.emptyList());


    when(iUserRepository.findById(anyString())).thenReturn(Optional.of(creator));
    when(iPlaylistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(playlist));

    SongNotFoundException exception= assertThrows(SongNotFoundException.class,()->{
        playlistservice.playPlaylist(creator, playlist);
    });

    assertEquals("Song not found", exception.getMessage());
  }

  
}
