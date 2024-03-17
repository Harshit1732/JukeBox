package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IPlaylistservice;
import com.crio.jukebox.services.ISongservice;

public class DeleteSongCommand implements ICommand {


    private IPlaylistservice playlistservice;
    private ISongservice songservice;

    public DeleteSongCommand(IPlaylistservice playlistservice, ISongservice songservice)
    {
        this.playlistservice= playlistservice;
        this.songservice= songservice;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId= tokens.get(2);
        String playlistId= tokens.get(3);
        List<Song> songs= new ArrayList<>();
        for(int i=4;i<tokens.size();i++)
        {
            Song song= songservice.getSongById(tokens.get(i));
            songs.add(song);
        }
        User user= new User("ram", userId);
        Playlist playlist= new Playlist(playlistId, user, "playlist-1", Collections.emptyList());
       Playlist deletedPlaylist= playlistservice.deleteSong(user, playlist, songs);
       System.out.println("Playlist ID - "+deletedPlaylist.getId());
       System.out.println("Playlist Name - "+deletedPlaylist.getName());
       System.out.print("Song IDs - ");
       List<Song> t= deletedPlaylist.getSongs();
       StringJoiner joiner = new StringJoiner(" ");
       t.forEach(song -> joiner.add(String.valueOf(song.getId())));
       System.out.println(joiner.toString());
    }

   
}
