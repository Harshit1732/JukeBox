package com.crio.jukebox.exceptions;

public class NoSuchUserFoundException extends RuntimeException {

    public NoSuchUserFoundException()
    {
     super();
    }
    public NoSuchUserFoundException(String msg)
    {
     super(msg);
    }
    
}
