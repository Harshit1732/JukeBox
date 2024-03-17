package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IUserservice;



public class CreateUserCommand implements ICommand {



    private IUserservice userService;

    public CreateUserCommand(IUserservice userService)
    {
        this.userService= userService;
    }

    @Override
    public void execute(List<String> tokens) {

        String userName= tokens.get(1);
       User user= userService.createUser(userName);
    
       System.out.println(user.getId()+" "+user.getName());

    }

    
}
