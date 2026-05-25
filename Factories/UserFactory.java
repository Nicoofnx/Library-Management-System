package Factories;
import Entities.User;

abstract class UserFactory {
    public abstract User createUser(int id,String name, String surname);
}
