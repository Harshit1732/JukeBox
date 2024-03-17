package com.crio.jukebox.exceptions;

public class NoRunningSongException extends RuntimeException {
    
    public NoRunningSongException()
    {
     super();
    }
    public NoRunningSongException(String msg)
    {
     super(msg);
    }
    
}
