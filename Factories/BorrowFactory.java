package Factories;

import Entities.BorrowRecord;
import Entities.User;
import Entities.Book;

public class BorrowFactory {
    public BorrowRecord createRecord(User user, Book book){
        BorrowRecord record = new BorrowRecord(user, book);
        return record;
    }
}
