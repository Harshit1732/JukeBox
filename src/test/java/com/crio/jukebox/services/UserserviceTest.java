package com.crio.jukebox.services;

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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import net.bytebuddy.build.Plugin.Engine.Source.Empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DisplayName("UserServiceTest") // Corrected the display name
@ExtendWith(MockitoExtension.class)
public class UserserviceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IPlaylistRepository playlistRepository;

    @InjectMocks
    private Userservice userService; // Corrected the service name

    @Test
    @DisplayName("Create user should create a user")
    public void createUser_shouldCreateUser() {
        User expected = new User("kiran", "1");

        when(userRepository.save(any(User.class))).thenReturn(expected);

        User actual = userService.createUser("kiran");

        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Get All users should return all users")

    public void getAllusers_shouldReturnUsers() {

        List<User> users = new ArrayList<User>() {
            {
                add(new User("Kiran", "1"));
                add(new User("Ram", "2"));
            }
        };

        List<User> expected = new ArrayList<User>() {
            {
                add(new User("Kiran", "1"));
                add(new User("Ram", "2"));
            }
        };
        
        when(userRepository.findAll()).thenReturn(expected);

        List<User> actual = userService.getAllUsers();

        Assertions.assertEquals(expected, actual);
    }


    @Test

    @DisplayName("Play song should play specific song")

    public void playSong_shouldPlaySpecificSong(){
        User creator = new User("kiran", "1");

     List<Song> songs= new ArrayList<Song>(){
        {
            add(new Song("1", "Song Title", "Genre", "Album", "Artist", CommonStatus.Running));
            add(new Song("2", "Song Title", "Genre", "Album", "Artist", CommonStatus.NOT_Running));
        }
     };

        Playlist existingPlaylist= new Playlist("1", creator, "playlist-1", songs);
        existingPlaylist.playPlaylist();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(playlistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(existingPlaylist));

        // Execute the method
        Song song =  new Song("2", "Song Title", "Genre", "Album", "Artist", CommonStatus.NOT_Running);
        Song playedSong = userService.playSong(creator,song);
    
       assertEquals(CommonStatus.Running, playedSong.getSongStatus());
    }



    @Test

    @DisplayName("Play song should throw user not found")

    public void playSong_shouldThrowUserNotfound(){
        User creator = new User("kiran", "1");
        Song existingSong = new Song("1", "Song Title", "Genre", "Album", "Artist", CommonStatus.NOT_Running);

        Playlist existingPlaylist= new Playlist("1", creator, "playlist-1", Arrays.asList(existingSong));
        existingPlaylist.playPlaylist();

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

     NoSuchUserFoundException exception= assertThrows(NoSuchUserFoundException.class, ()->{
         userService.playSong(creator, existingSong);
     });
       
       assertEquals("user not found", exception.getMessage());
    }


    @Test
    @DisplayName("Play song should throw PLaylist Not Selected")

    public void playSong_shouldThrowPLaylistNotSelected(){

        User creator = new User("kiran", "1");

       List<Song> songs= new ArrayList<Song>(){
        {
            add(new Song("2", "Song Title", "Genre", "Album", "Artist", CommonStatus.NOT_Running));
        }
       };
      
        Playlist existingPlaylist=  new Playlist("1", creator, "playlist-1", songs);
        existingPlaylist.playPlaylist();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(creator));
        when(playlistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(existingPlaylist));

        Song userSong = new Song("1", "Song Title", "Genre", "Album", "Artist", CommonStatus.NOT_Running);

        SongNotFoundException exception= assertThrows(SongNotFoundException.class,()->{
            userService.playSong(creator, userSong);
        });
       
        assertEquals("Playlist is not selected", exception.getMessage());
    }

     @Test
     @DisplayName("Switch to previous song should Play previous song")
    public void switchToPreviousSong_shouldPlaypreviousSongOfPlaylist(){

        User creator = new User("kiran", "1");

        List<Song> songs= new ArrayList<Song>(){
            {
            add(new Song("1", "Song 1", "Genre", "Album", "Artist", CommonStatus.Running));
            add( new Song("2", "Song 2", "Genre", "Album", "Artist", CommonStatus.NOT_Running));
            add(new Song("3", "Song 3", "Genre", "Album", "Artist", CommonStatus.NOT_Running));
            }

        };

    Playlist existingPlaylist = new Playlist("1", creator, "playlist-1", songs);
    existingPlaylist.playPlaylist();

    when(userRepository.findById(anyString())).thenReturn(Optional.of(creator));
    when(playlistRepository.getAllUserPlaylist(any(User.class))).thenReturn(Arrays.asList(existingPlaylist));

    // Action
    Song playedSong = userService.switchToPreviousSong(creator, "Back");

    // Assertion
    verify(playlistRepository, times(1)).getAllUserPlaylist(creator);

    // Ensure that the previous song (song1) is played
    assertEquals("3", playedSong.getId());
    // assertTrue(song1.getSongStatus() == CommonStatus.Running);
    // assertTrue(song2.getSongStatus() == CommonStatus.NOT_Running);
    // assertTrue(song3.getSongStatus() == CommonStatus.NOT_Running);
    }


    @Test
    @DisplayName("Switch to previous song should Throw user not found")
   public void switchToPreviousSong_shouldThrowUserNotfound(){

    User creator = new User("kiran", "1");

    Playlist existingPlaylist = new Playlist("1", creator, "playlist-1", Collections.emptyList());
    existingPlaylist.playPlaylist();

     when(userRepository.findById(anyString())).thenReturn(Optional.empty());

    NoSuchUserFoundException exception= assertThrows(NoSuchUserFoundException.class, ()->{
        userService.switchToPreviousSong(creator, "Back");
     });
        // Action
   // Assertion
   assertEquals("user not found", exception.getMessage());
   verify(playlistRepository, never()).getAllUserPlaylist(creator);
}

 
}