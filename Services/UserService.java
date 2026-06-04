package Services;

import Entities.Admin;
import Entities.Client;
import Entities.User;

import Factories.AdminFactory;
import Factories.ClientFactory;


import Repositories.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
    Registra un nuevo usuario en la biblioteca.
     */
    public Client addClientUser(String name, String surname,String email, String password) {

    for (User u : userRepository.getAllUsers()) { // Asegúrate de usar el método que te dé el mapa o la lista
        if (u.getName().equalsIgnoreCase(name) && u.getSurname().equalsIgnoreCase(surname) && u.getPassword().equalsIgnoreCase(password) && u.getEmail().equalsIgnoreCase(email)) {
            System.out.println("El usuario " + name + " " + surname + " ya está registrado.");
            return (Client) u;
        }
    }

        int nuevoId = userRepository.getAllUsers().size() + 1;
        ClientFactory factory = new ClientFactory();
        Client client = factory.createUser(nuevoId, name, surname,email,password);
        userRepository.addUser(client);

        return client;
    }

    /*
    Registra un nuevo usuario en la biblioteca.
     */
    public Admin addAdminUser(String name, String surname,String email, String password) {
        for (User u : userRepository.getAllUsers()) { 
            if (u.getName().equalsIgnoreCase(name) && u.getSurname().equalsIgnoreCase(surname) && u.getPassword().equalsIgnoreCase(password) && u.getEmail().equalsIgnoreCase(email)) {
                System.out.println(" El usuario" + name + " " + surname + " ya está registrado.");
                return (Admin) u;
            }
        }

        int nuevoId = userRepository.showUsersWithID().size() + 1;
        AdminFactory adminFactory = new AdminFactory();
        Admin admin = adminFactory.createUser(nuevoId, name, surname,email,password);
        userRepository.addUser(admin);

        return admin;
    }

    public User login(String email, String password){
        User user = userRepository.getUserByEmail(email);

        if(user != null && user.getPassword().equalsIgnoreCase(password)){
            return user;
        }

        return null;
    }

    public void updateUserInfo(int id, String name, String surname){
        User user = userRepository.getUser(id);
        user.setName(name);
        user.setSurname(surname);
        userRepository.updateUser(id);
    }

    //Sistema de baneos
    public void banUserFromLibrary(int userID){
        User user = userRepository.getUser(userID);
        if (user != null) {
            user.updateUserStatus(true); 
            userRepository.saveUsers(); 
            System.out.println("Usuario " + user.getName() + " baneado correctamente.");
        }
        
        if (user == null) {
            System.out.println("Usuario no encontrado");
        }
    }

    public void unbanUserFromLibrary(int userID){
        User user = userRepository.getUser(userID);
        if (user != null) {
            user.updateUserStatus(false); 
            userRepository.saveUsers(); 
            System.out.println("Usuario " + user.getName() + " desbaneado correctamente.");
        }

        if (user == null) {
            System.out.println("Usuario no encontrado");
        }
    }
}