
package com.crio.jukebox.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import com.crio.jukebox.exceptions.SongNotFoundException;


public class Playlist extends BaseEntity{

    private String playlistName;
    private User creator;
    private List<Song> songs;
    private  CommonStatus playlistStatus;

    public Playlist(String playlistName, String id,User creator, CommonStatus playlistStatus)
    {
        this.id=id;
        this.playlistName= playlistName;
        this.creator= creator;
        this.songs= new ArrayList<Song>();
        this.playlistStatus= playlistStatus;
    }
    public Playlist( String playlistName, User creator, List<Song> songs)
    {
        this.playlistName= playlistName;
        this.creator= creator;
        this.songs= songs;
    }
    public Playlist( String id, User creator, String playlistName, List<Song> songs)
    {
        this.id=id;
        this.playlistName= playlistName;
        this.creator= creator;
        this.songs= songs;
    }


    public String getName(){
        return this.playlistName;
    }

    public User getcreator(){
        return this.creator;
    }

    public List<Song> getSongs(){
        return new ArrayList<>(this.songs);
    }

    public CommonStatus getPlaylistStatus(){
        return playlistStatus;
    }

    public void playPlaylist(){
        this.playlistStatus= CommonStatus.Running;
    }

    public void setSong(List<Song> songs)
    {
        this.songs= songs;
    }

    public void addSong(Song song)
    {
        List<Song> songs= getSongs();
        boolean isSongavailable= songs.stream().anyMatch(e->e.equals(song));
        if(isSongavailable==false)
        {
            songs.add(song);
        }else{
            throw  new RuntimeException("Song already present");
        }
        setSong(songs);
    }

    public void removeSong(Song song)
    {
        Song foundSong = songs.stream()
        .filter(e -> e.getId().equals(song.getId()))
        .findFirst()
        .orElseThrow(() -> new SongNotFoundException("Song not found in playlist"));

        List<Song> songs= getSongs();
        songs.remove(foundSong);
        setSong(songs);
    }

    
}
