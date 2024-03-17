package com.crio.jukebox.respositories;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.User;

public interface CRUDRepository<T,ID>{
    public T save(T entity);

    public List<T> findAll();
    public  Optional<T> findById(ID id);
    
    public void deleteById(ID id);
    
}
