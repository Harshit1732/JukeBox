package com.crio.jukebox.commands;

import java.util.Collections;
import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IPlaylistservice;

public class DeletePlaylistCommand  implements ICommand{

    private IPlaylistservice playlistservice;

    public DeletePlaylistCommand(IPlaylistservice playlistservice)
    {
        this.playlistservice= playlistservice;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId= tokens.get(1);
        String playlistId= tokens.get(2);
     
        User user= new User("ram", userId);
        Playlist playlist= new Playlist(playlistId, user, "playlist-1", Collections.emptyList());
        playlistservice.deletePlaylist(user,playlist);
        System.out.println("Delete Successful");
    }
    
}
