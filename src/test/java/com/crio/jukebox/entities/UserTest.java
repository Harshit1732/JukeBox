package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserTest")
public class UserTest {


    @Test
    @DisplayName("checkPlaylist should return true platlist is found")
    public void checkPlaylist_ShouldReturnTrue_Playlistisfound(){
        String id= "1";
        String name="Playlist 1";

        List<Song> songs= new ArrayList<Song>(){
            {
              add( new Song("1","South of the Border", "Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
              add( new Song("2","Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
            }
        };
      User creator= new User("admin", "1");
      Playlist playlist= new Playlist("1", creator, "playlist-1", Collections.emptyList());
      User user= new User("user", "2",  new ArrayList<Playlist>(){{add(playlist);}});

      Assertions.assertTrue(user.checkplaylist(playlist));

    }


    @Test
    @DisplayName("checkplaylist should return false playlist is not found")
    public void checkplaylist_ShouldReturnFalse_PlaylistisNotfound(){

        String id= "1";
        String name="Playlist 1";

        List<Song> songs= new ArrayList<Song>(){
            {
              add( new Song("1","South of the Border", "Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
              add( new Song("2","Remember the Name","Pop","No.6 Collaborations Project","Ed Sheeran", CommonStatus.NOT_Running));
            }
        };
      User creator= new User("admin", "1");
      Playlist playlist= new Playlist("1", creator, "playlist-1", Collections.emptyList());
      User user= new User("user", "2");

      Assertions.assertFalse(user.checkplaylist(playlist));

    }

    
}
