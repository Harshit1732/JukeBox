package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IPlaylistservice;
import com.crio.jukebox.services.ISongservice;

public class CreatePlaylistCommand implements ICommand{

    
    private IPlaylistservice playlistservice;
    private ISongservice songservice;

    public CreatePlaylistCommand(IPlaylistservice playlistservice, ISongservice songservice)
    {
        this.playlistservice= playlistservice;
        this.songservice= songservice;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId= tokens.get(1);
        String playlistName= tokens.get(2);
        List<Song> songs= new ArrayList<>();
        for(int i=3;i<tokens.size();i++)
        {
            Song song= songservice.getSongById(tokens.get(i));
            songs.add(song);
        }

        User user= new User("ram", userId);
        Playlist playlist =playlistservice.createPlaylist(user, playlistName, songs);
        System.out.println("Playlist ID - "+playlist.getId());

    }


}
