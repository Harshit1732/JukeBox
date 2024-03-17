package com.crio.jukebox.services;

import java.util.List;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.NoRunningSongException;
import com.crio.jukebox.exceptions.NoSuchUserFoundException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.respositories.IPlaylistRepository;
import com.crio.jukebox.respositories.IUserRepository;
import com.crio.jukebox.respositories.Playlistrepository;
import com.crio.jukebox.respositories.UserRepository;


public class Userservice implements IUserservice {

    private IUserRepository userRepository;
    private IPlaylistRepository playlistRepository;
   

    private IPlaylistservice playlistservice;
    public Userservice(IUserRepository userRepository, IPlaylistservice playlistservice, IPlaylistRepository playlistRepository)
    {
        this.userRepository= userRepository;
        this.playlistservice= playlistservice;
        this.playlistRepository= playlistRepository;
    }

    @Override
    public User createUser(String name) {
        User user = new User(name);
       return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users= userRepository.findAll();
        return users;
    }


    // @Override
    // public void choosePlaylist(User creator, Playlist playlist) {
    //     userRepository.findById(creator.getId()).orElseThrow(()->new NoSuchUserFoundException("user not found"));
    //     List<Playlist> playlists= playlistRepository.getAllUserPlaylist(creator);
    //      playlists.stream()
    //     .filter(e -> e.getId().equals(playlist.getId()))
    //     .findFirst()
    //     .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));

    //     playlistservice.playPlaylist(creator, playlist);
    // }


    @Override
    public Song playSong(User creator, Song song) {
        userRepository.findById(creator.getId()).orElseThrow(()-> new NoSuchUserFoundException("user not found"));
        List<Song> songs = getSongsOfPlaylist(creator);
        Song currentRunningSong= songs.stream()
        .filter(e -> CommonStatus.Running.equals(e.getSongStatus()))
        .findFirst()
        .orElseThrow(() -> new SongNotFoundException("Playlist is not selected"));
       
        Song foundSong = songs.stream()
                .filter(e -> e.getId().equals(song.getId()))
                .findFirst()
                .orElseThrow(() -> new SongNotFoundException("Given song id is not a part of the active playlist"));
    
        foundSong.playSong();
        currentRunningSong.stopSong();
        return foundSong;
    }
    

    public List<Song> getSongsOfPlaylist(User creator) {
        List<Playlist> playlists = playlistRepository.getAllUserPlaylist(creator);
        if (playlists == null || playlists.isEmpty()) throw new PlaylistNotFoundException("No playlists found for the user");
            
        Playlist playlist = playlists.stream()
            .filter(e -> e.getPlaylistStatus() == CommonStatus.Running)
            .findFirst()
            .orElseThrow(() -> new PlaylistNotFoundException("playlist is not choosen"));

         return playlist.getSongs();
    }
    

    @Override
    public Song switchToPreviousSong(User creator, String Back) {
        userRepository.findById(creator.getId())
        .orElseThrow(() -> new NoSuchUserFoundException("user not found"));

        List<Song> songs = getSongsOfPlaylist(creator);

        Song prevSong = songs.stream()
                .filter(song -> song.getSongStatus() == CommonStatus.Running)
                .findFirst()
                .map(song -> {
                    song.stopSong();
                    int currentIndex = songs.indexOf(song);
                    int prevIndex = (currentIndex - 1 + songs.size()) % songs.size();
                    Song previousSong = songs.get(prevIndex);
                    previousSong.playSong();
                    return previousSong;
                })
                .orElseThrow(() -> new NoRunningSongException("No running song found"));

        return prevSong;
    }
    
    @Override
    public Song switchToNextSong(User creator, String Next) { 
        userRepository.findById(creator.getId())
        .orElseThrow(() -> new NoSuchUserFoundException("user not found"));
        List<Song> songs = getSongsOfPlaylist(creator);
        Song nextSong = songs.stream()
                .filter(song -> song.getSongStatus() == CommonStatus.Running)
                .findFirst()
                .map(song -> {
                    song.stopSong();
                    int currentIndex = songs.indexOf(song);
                    int nextIndex = (currentIndex + 1) % songs.size();
                    Song next = songs.get(nextIndex);
                    next.playSong();
                    return next;
                })
                .orElseThrow(() -> new NoRunningSongException("No running song found"));
        return nextSong;
    }

}
