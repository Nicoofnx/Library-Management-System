package Services;

import Entities.Admin;
import Entities.Client;
import Entities.User;

import Factories.AdminFactory;
import Factories.ClientFactory;


import Repositories.UserRepository;

public class UserService {

    private UserRepository repository;
    private int userID = 0;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    /*
    Registra un nuevo usuario en la biblioteca.
     */
    public Client addClientUser(String name, String surname) {
        userID++;

        ClientFactory factory = new ClientFactory();
        Client client = factory.createUser(userID, name, surname);
        repository.addUser(client);

        return client;
    }

    /*
    Registra un nuevo usuario en la biblioteca.
     */
    public Admin addAdminUser(String name, String surname) {
        userID++;

        AdminFactory adminFactory = new AdminFactory();
        Admin admin = adminFactory.createUser(userID, name, surname);
        repository.addUser(admin);

        return admin;
    }

    //Sistema de baneos
    public void banUserFromLibrary(User user){
        user.updateUserStatus(true);
    }

    public void unbanUserFromLibrary(User user){
        user.updateUserStatus(false);
    }
}
