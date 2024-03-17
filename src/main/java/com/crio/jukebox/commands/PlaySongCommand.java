package com.crio.jukebox.commands;

import java.util.List;
import java.util.StringJoiner;
import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.NoSuchCommandException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.services.IUserservice;
import com.crio.jukebox.entities.Song;


public class PlaySongCommand implements ICommand {

   private CommandInvoker commandInvoker;
   private IUserservice userservice;

   public PlaySongCommand(CommandInvoker commandInvoker, IUserservice userservice)
   {
    this.commandInvoker= commandInvoker;
    this.userservice= userservice;
   }

    @Override
    public void execute(List<String> tokens) throws NoSuchCommandException {

        String switchSong= tokens.get(2);
        if (!"NEXT".equals(switchSong) && !"BACK".equals(switchSong)) 
        {
          try{
            String userId= tokens.get(1);
            String songId= tokens.get(2);
         
            User user= new User("ram", userId);
            Song song = new Song(songId, "South of the Border","Pop","No.6 Collaborations Project","Ed Sheeran",CommonStatus.NOT_Running);
            Song runningSong= userservice.playSong(user, song);
            System.out.println("Current Song Playing");
            System.out.println("Song - "+runningSong.getName());
            System.out.println("Album - "+runningSong.getAlbumName());
            System.out.print("Artists - ");
            List<String> t= runningSong.getArtistList();
            StringJoiner joiner = new StringJoiner(",");
            t.forEach(joiner::add);
            System.out.println(joiner.toString());
          }catch(SongNotFoundException e)
          {
              System.out.println(e.getMessage());
          }
            
        }else{
            commandInvoker.executeCommand(tokens.get(2), tokens);
        }
        
    }
    
    
}
