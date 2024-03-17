package com.crio.jukebox.respositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.User;


public class UserRepository implements IUserRepository {

    private final Map<String, User> userMap;
    private Integer autoincrement=0;

    public UserRepository(){
        this.userMap=  new HashMap<>();
    }


    @Override
    public User save(User entity) {
      String id= entity.getId();
      if(entity.getId()==null)
      {
         autoincrement++;
         User user= new User(entity.getName(),Integer.toString(autoincrement));
         userMap.put(Integer.toString(autoincrement),user);
         return user;
      }
      userMap.put(id,entity);
      return entity;
    }

    @Override
    public List<User> findAll() {
       List<User> users= userMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
       return users;
    }

    @Override
    public Optional<User> findById(String id) {
       return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        
    }




}
