package com.crio.jukebox.respositories;

import com.crio.jukebox.entities.Song;

public interface ISongRepository extends CRUDRepository<Song,String>  {
    public Song findByid(String id);
}
