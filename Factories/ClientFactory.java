package Factories;

import Entities.Client;

public class ClientFactory extends UserFactory{
    @Override
    public Client createUser(int id, String name, String surname){
        return new Client(id, name, surname);
    }
}

