
package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.respositories.Data.Dataloader;
import com.crio.jukebox.respositories.Data.SongData;


public class LoadDataCommand implements ICommand {

     private Dataloader dataloader;

     public LoadDataCommand(Dataloader dataloader)
     {
        this.dataloader= dataloader;
     }

    // private SongData songdata;

    // public LoadDataCommand(SongData songdata)
    // {
    //     this.songdata= songdata;
    // }


    @Override
    public void execute(List<String> tokens) {

        String dataName= tokens.get(0);
        String fileName= tokens.get(1);

        dataloader.executeData(dataName, fileName);
        System.out.println("Songs Loaded successfully");
    }
    
    
}
