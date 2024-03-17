package com.crio.jukebox.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;



public class Song extends BaseEntity {
    private String songName;
    private String genre;
    private String albumName;
    private String albumArtist;
    private List<String> artistList;
    private CommonStatus songStatus;

    public Song(String id,String songName, String genre, String albumName, String albumArtist, CommonStatus songStatus){

        this.id= id;
        this.songName= songName;
        this.genre= genre;
        this.albumName= albumName;
        this.albumArtist= albumArtist;
        this.songStatus= songStatus;
    }

    public Song(String id,String songName, String genre, String albumName, String albumArtist, CommonStatus songStatus, List<String> artist){

        this.id= id;
        this.songName= songName;
        this.genre= genre;
        this.albumName= albumName;
        this.albumArtist= albumArtist;
        this.songStatus= songStatus;
        this.artistList= artist;
    }

    public String getName(){
        return this.songName;
    }
    public String getGenre(){
        return this.genre;
    }
    public String getAlbumName(){
        return this.albumName;
    }
    public String getAlbumArtist(){
        return this.albumArtist;
    }
    public CommonStatus getSongStatus(){
        return this.songStatus;
    }

    public List<String> getArtistList(){
        return this.artistList;
    }

    public void playSong(){
        this.songStatus= CommonStatus.Running;
    }

    public void stopSong(){
        this.songStatus= CommonStatus.NOT_Running;
    }


    // @Override
    // public String toString() {
    //     return "User [id=" + id + ", name=" + name + "]";
    // }
   
    
}
