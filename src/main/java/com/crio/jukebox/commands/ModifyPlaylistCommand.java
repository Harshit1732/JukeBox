package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.exceptions.NoSuchCommandException;

public class ModifyPlaylistCommand  implements ICommand{

    private CommandInvoker commandInvoker;

    public ModifyPlaylistCommand(CommandInvoker commandInvoker)
    {
        this.commandInvoker= commandInvoker;
    }

    @Override
    public void execute(List<String> tokens) throws NoSuchCommandException {
        String songModify= tokens.get(1);
        commandInvoker.executeCommand(songModify, tokens);
    }
}