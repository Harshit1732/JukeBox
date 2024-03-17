package com.crio.jukebox.commands;

import java.util.List;
import java.util.StringJoiner;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IUserservice;

public class BackSongCommand implements ICommand {

    private IUserservice userService;

    public BackSongCommand(IUserservice userService)
    {
        this.userService= userService;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId= tokens.get(1);
        String toSwitch= tokens.get(2);
     
        User user= new User("ram", userId);
       Song song= userService.switchToPreviousSong(user, toSwitch);
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
