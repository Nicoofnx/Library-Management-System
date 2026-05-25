package Repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Entities.User;

public class UserRepository {
    /** Mapa de usuarios registrados */
    private HashMap<Integer, User> users = new HashMap<>();

    //CREATE users
    public void addUser(User user){
        users.put(user.getID(),user);
    }

    //READ users
    public User getUser(int id){
        if(id == 0){
            System.out.println("El usuario no existe");
        }
        return users.get(id);
    }

    //UPDATE users
    public void updateUserInfo(int id, String name, String surname){
        User user = getUser(id);

        user.setName(name);
        user.setSurname(surname);
    }

    //DELETE users
    public void deleteUser(int id){
        users.remove(id);
    }

    public Set<Map.Entry<Integer,User>> showUsersWithID(){
        return users.entrySet();
    }
}
