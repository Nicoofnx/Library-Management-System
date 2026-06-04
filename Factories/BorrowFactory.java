package Factories;

import Entities.BorrowRecord;
import Entities.User;
import Entities.Book;

public class BorrowFactory {
    public BorrowRecord createRecord(User user, Book book){
        return new BorrowRecord(
            user,
            book
        );
    }
}
