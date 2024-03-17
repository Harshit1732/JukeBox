package com.crio.jukebox.services;

import java.util.List;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.NoSuchUserFoundException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.respositories.IPlaylistRepository;
import com.crio.jukebox.respositories.IUserRepository;

public class Playlistservice implements IPlaylistservice {
    private IPlaylistRepository playlistRepository;
    private IUserRepository userRepository;

    public Playlistservice(IPlaylistRepository playlistRepository, IUserRepository userRepository){
         this.playlistRepository= playlistRepository;
         this.userRepository= userRepository;
    }

    @Override
    public Playlist createPlaylist(User creator, String name, List<Song> songs) {
        userRepository.findById(creator.getId())
        .orElseThrow(() -> new NoSuchUserFoundException("user is not found"));

        Playlist playlist= new Playlist(name, creator, songs);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist addSongs(User creator, Playlist playlist, List<Song> newsongs) {
        Playlist fetchPlaylist= validateUserandPlaylist(creator, playlist);
        if(newsongs.isEmpty()) throw new SongNotFoundException("Song not found");

        newsongs.stream()
            .forEach(song -> {
                try {
                    fetchPlaylist.addSong(song);
                } catch (SongNotFoundException e) {
                    throw new SongNotFoundException("Song not found");
                }
            });
        return playlistRepository.save(fetchPlaylist);
    }

    @Override
    public Playlist deleteSong(User creator, Playlist playlist, List<Song> songs) {

        Playlist fetchPlaylist= validateUserandPlaylist(creator, playlist);
        songs.forEach(song -> {
            try {
                fetchPlaylist.removeSong(song);
            } catch (SongNotFoundException e) {
                throw new SongNotFoundException("Song not found");
            }
        });
        return playlistRepository.save(fetchPlaylist);
    }

    @Override
    public void deletePlaylist(User creator, Playlist playlist) {
        Playlist fetchplaylist=validateUserandPlaylist(creator, playlist);
        playlistRepository.deletePlaylist(fetchplaylist);
    }

   
    @Override
    public Song playPlaylist(User creator, Playlist playlist) {
       Playlist fetchPlaylist= validateUserandPlaylist(creator, playlist);
       List<Song> songs = fetchPlaylist.getSongs();
       if(!songs.isEmpty()) {
            fetchPlaylist.playPlaylist();
            songs.get(0).playSong();
            return songs.get(0);
        } else {
            throw new SongNotFoundException("No song is found");
        }
    }
    

    public Playlist validateUserandPlaylist(User creator, Playlist playlist)
    {
        User user = userRepository.findById(creator.getId()).orElseThrow(() -> new NoSuchUserFoundException("User is not found"));

        return playlistRepository.getAllUserPlaylist(creator).stream()
                                    .filter(e -> e.getId().equals(playlist.getId()))
                                    .findFirst()
                                    .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));
    }
}
