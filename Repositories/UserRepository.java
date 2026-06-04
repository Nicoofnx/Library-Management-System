package Repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import GSONAdapters.LocalDateAdapter;
import GSONAdapters.UserAdapter;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;

import Entities.User;


public class UserRepository {

    /** Mapa de usuarios registrados */
    private HashMap<Integer, User> users = new HashMap<>();
    String filePath = "E:/Users/nicol/Desktop/JavaSecondPractice/Data/users.json";

    //JSON de Users
    Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class,new LocalDateAdapter())
    .registerTypeHierarchyAdapter(User.class, new UserAdapter())
    .setPrettyPrinting()
    .create();

    public UserRepository(){
        readUsers();
    }

    //Deserializador del Users.json, se asegura de que los users que estuvieron alguna vez en json se mantengan y se mantengan en el map.
    public void readUsers(){

    java.io.File file = new java.io.File(filePath);
    
    if (!file.exists() || file.length() == 0) {
        this.users = new HashMap<>();
        System.out.println("Base de datos de usuarios vacía. Iniciando mapa limpio.");
        return; 
    }

        try(FileReader fileReader = new FileReader(filePath)) {
            java.lang.reflect.Type tipoMapa = new com.google.gson.reflect.TypeToken<HashMap<Integer, User>>(){}.getType();
            HashMap<Integer,User> loadedUsers = gson.fromJson(fileReader,tipoMapa);     
            
            if(loadedUsers != null){
                this.users = loadedUsers;
            }
        } catch (IOException e) {
            System.out.println("Se ha detectado un error de tipo: "+ e.getMessage());
        }
    }
    
    public void saveUsers() {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(this.users, fileWriter);            
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    

    //CREATE users
    public void addUser(User user){
        users.put(user.getID(),user);
        saveUsers();
    }

    //READ users
    public User getUser(int id){
        if(id == 0 || id < 0){
            System.out.println("El usuario no existe");
            return null;
        }

        return users.get(id);
    }

    public User getUserbyName(String name){
        for(User userInRepo : getAllUsers()){
            String nameOfUser = userInRepo.getName();
            if (nameOfUser.equalsIgnoreCase(name)) {
                return userInRepo;                
            }
        }
        return null;
    }

    public User getUserByEmail(String email){
        for(User userInRepo : getAllUsers()){
            String emailOfUser = userInRepo.getEmail();
            if(emailOfUser.equalsIgnoreCase(email)){
                return userInRepo;
            }
        }
        return null;
    }


    public void updateUser(int id){
        User user = getUser(id);
        this.users.put(user.getID(), user);

        saveUsers();
    }

    //DELETE users
    public void deleteUser(int id){
        users.remove(id);
        saveUsers();
    }

    public Set<Map.Entry<Integer,User>> showUsersWithID(){
        return users.entrySet();
    }

    public Collection<User> getAllUsers(){
        return users.values();
    }
}
