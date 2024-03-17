
package com.crio.jukebox.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class User extends BaseEntity {

    public String name;
    
    public List<Playlist> playlists;
    public User(String name, String id)
    {
        this.id= id;
        this.name=name;
        this.playlists= new ArrayList<>();
    }

    public User(String name, String id, List<Playlist> playlists)
    {
        this.id=id;
        this.name= name;
        this.playlists= playlists;
    }
    public User(String name)
    {
        this.name= name;
        this.playlists= new ArrayList<>();
    }


    public User() {}

    public boolean checkplaylist(Playlist playlist)
    {
        return playlists.stream().anyMatch(e->e.equals(playlist));
    }
    public String getName(){
        return this.name;
    }

    public User orElseThrow(Object object) {
        return null;
    }

}
