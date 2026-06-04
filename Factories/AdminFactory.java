package Factories;

import Entities.Admin;

public class AdminFactory extends UserFactory{
    @Override
    public Admin createUser(int id, String name, String surname, String email, String password){
        return new Admin(
            id,
            name,
            surname,
            email,
            password
        );
    }
}
