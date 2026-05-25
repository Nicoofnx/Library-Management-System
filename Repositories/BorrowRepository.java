package Repositories;

import Entities.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Entities.BorrowRecord;

public class BorrowRepository {
    /** Historial de préstamos de la biblioteca */
    private HashMap<Integer, BorrowRecord> borrowedBooks = new HashMap<>();
    private int BorrowRecordID = 0;
    
    //CREATE record
    public void addBorrowRecord(BorrowRecord record){
        BorrowRecordID++;
        borrowedBooks.put(BorrowRecordID, record);
    }

    //READ record
    public BorrowRecord getBorrowedRecord(int id){
        if(id == 0){
            System.out.println("Record no encontrado.");
        }

        return borrowedBooks.get(id);
    }

    //UPDATE record
    public void updateRecord(int id,User user,Book book){
        BorrowRecord record = getBorrowedRecord(id);

        record.setUser(user);
        record.setBook(book);
    }

    //DELETE record
    public void deleteRecord(int id){
        borrowedBooks.remove(id);
    }

    public Collection<BorrowRecord> getAllRecords(){
        return borrowedBooks.values();
    }

    public Set<Map.Entry<Integer, BorrowRecord>> getAllRecordwithID(){
        return borrowedBooks.entrySet();
    }
}
