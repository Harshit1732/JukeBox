package com.crio.jukebox.Config;

import com.crio.jukebox.commands.CreateUserCommand;
import com.crio.jukebox.commands.DeletePlaylistCommand;
import com.crio.jukebox.commands.DeleteSongCommand;
import com.crio.jukebox.commands.LoadDataCommand;
import com.crio.jukebox.commands.ModifyPlaylistCommand;
import com.crio.jukebox.commands.NextSongCommand;
import com.crio.jukebox.commands.PlayPlaylistCommand;
import com.crio.jukebox.commands.PlaySongCommand;

import com.crio.jukebox.commands.AddSongCommand;
import com.crio.jukebox.commands.BackSongCommand;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.commands.CreatePlaylistCommand;
import com.crio.jukebox.respositories.IPlaylistRepository;
import com.crio.jukebox.respositories.ISongRepository;
import com.crio.jukebox.respositories.IUserRepository;
import com.crio.jukebox.respositories.Playlistrepository;
import com.crio.jukebox.respositories.Songrepository;
import com.crio.jukebox.respositories.UserRepository;
import com.crio.jukebox.respositories.Data.Dataloader;
import com.crio.jukebox.respositories.Data.SongData;
import com.crio.jukebox.services.IPlaylistservice;
import com.crio.jukebox.services.ISongservice;
import com.crio.jukebox.services.IUserservice;
import com.crio.jukebox.services.Playlistservice;
import com.crio.jukebox.services.Songservice;
import com.crio.jukebox.services.Userservice;

public class ApplicationConfiguration {

    //repository
   private final  IUserRepository userRepository= new UserRepository();

   private final IPlaylistRepository playlistRepository= new Playlistrepository();

   private final ISongRepository songRepository= new Songrepository();

   //services

   private final IPlaylistservice playlistservice= new Playlistservice(playlistRepository, userRepository);
   private final IUserservice userservice= new Userservice(userRepository, playlistservice, playlistRepository);
   private final ISongservice songservice= new Songservice(songRepository);


   // dataloader
   private Dataloader dataloader= new Dataloader();
   private CommandInvoker commandInvoker= new CommandInvoker();

   //Commands

   private final AddSongCommand addSongCommand= new AddSongCommand(playlistservice, songservice);
   private final CreatePlaylistCommand  createPlaylistCommand= new CreatePlaylistCommand(playlistservice,songservice);
   private final CreateUserCommand createUserCommand= new CreateUserCommand(userservice);
   private final DeletePlaylistCommand deletePlaylistCommand= new DeletePlaylistCommand(playlistservice);
   private final DeleteSongCommand  deleteSongCommand= new DeleteSongCommand(playlistservice,songservice);
   private final LoadDataCommand loadDataCommand= new LoadDataCommand(dataloader);
   private final PlayPlaylistCommand playPlaylistCommand= new PlayPlaylistCommand(playlistservice);
   private final PlaySongCommand playSongCommand= new PlaySongCommand(commandInvoker,userservice);
   private final BackSongCommand backSongCommand= new BackSongCommand(userservice);
   private final NextSongCommand nextSongCommand= new NextSongCommand(userservice);
   private final ModifyPlaylistCommand modifyPlaylistCommand=  new ModifyPlaylistCommand(commandInvoker);




  public CommandInvoker getCommandInvoker(){
    commandInvoker.register("CREATE-USER", createUserCommand);
    commandInvoker.register("CREATE-PLAYLIST", createPlaylistCommand);
    commandInvoker.register("DELETE-PLAYLIST", deletePlaylistCommand);
    commandInvoker.register("PLAY-PLAYLIST", playPlaylistCommand);
    commandInvoker.register("ADD-SONG", addSongCommand);
    commandInvoker.register("DELETE-SONG", deleteSongCommand);
    commandInvoker.register("MODIFY-PLAYLIST",modifyPlaylistCommand );
    commandInvoker.register("PLAY-SONG", playSongCommand);
    commandInvoker.register("NEXT", nextSongCommand);
    commandInvoker.register("BACK", backSongCommand);
    commandInvoker.register("LOAD-DATA", loadDataCommand);

    return commandInvoker;

  }

  public Dataloader getDataLoader(){
    dataloader.register("LOAD-DATA", new SongData(songRepository));
    return dataloader;
  }





}
