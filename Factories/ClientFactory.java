package Factories;

import Entities.Client;

public class ClientFactory extends UserFactory{
    @Override
    public Client createUser(int id, String name, String surname, String email, String password){
        return new Client(
            id,
            name,
            surname,
            email,
            password
        );
    }
}

