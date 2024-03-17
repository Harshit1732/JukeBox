package com.crio.jukebox.commands;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IPlaylistservice;

public class PlayPlaylistCommand implements ICommand {

    private IPlaylistservice playlistservice;

    public PlayPlaylistCommand(IPlaylistservice playlistservice)
    {
        this.playlistservice= playlistservice;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId= tokens.get(1);
        String playlistId= tokens.get(2);
     
        User user= new User("ram", userId);
        Playlist playlist= new Playlist(playlistId, user, "playlist-1", Collections.emptyList());
        Song song= playlistservice.playPlaylist(user,playlist);

        System.out.println("Current Song Playing");
        System.out.println("Song - "+song.getName());
        System.out.println("Album - "+song.getAlbumName());
        System.out.print("Artists - ");
        List<String> t= song.getArtistList();
        StringJoiner joiner = new StringJoiner(",");
        t.forEach(joiner::add);
        System.out.println(joiner.toString());
    }
    
    
}
