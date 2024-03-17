package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.exceptions.NoSuchCommandException;

public interface ICommand {

    void execute(List<String> tokens) throws NoSuchCommandException;
    
}
