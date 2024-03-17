package com.crio.jukebox.respositories.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.crio.jukebox.entities.CommonStatus;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.respositories.ISongRepository;

public class SongData implements IData {

    private ISongRepository songRepository;

    public SongData( ISongRepository songRepository)
    {
        this.songRepository= songRepository;
    }


    @Override
    public void loadData(String datapath) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(datapath));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(","));
                if(tokens.size()==0)
                {
                    break;
                }
                List<String> artists= Arrays.asList(tokens.get(5).split("#"));
                addSong(tokens.get(0),tokens.get(1),tokens.get(2), tokens.get(3), tokens.get(4), artists);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void addSong(String id, String name, String genre, String albumName, String albumArtist, List<String> artist)
    {
        this.songRepository.save(new Song(id,name,genre,albumName, albumArtist, CommonStatus.NOT_Running, artist));
    }
    
}
