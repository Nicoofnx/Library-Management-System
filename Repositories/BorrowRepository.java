package Repositories;

import Entities.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import GSONAdapters.*;

public class BorrowRepository {
    private UserRepository userRepository;
    private HashMap<Integer, BorrowRecord> borrowedBooks = new HashMap<>();
    private int recordID = 1;
    String filePath = "E:/Users/nicol/Desktop/JavaSecondPractice/Data/records.json";

    Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class,new LocalDateAdapter())
    .registerTypeHierarchyAdapter(User.class, new UserAdapter())
    .setPrettyPrinting()
    .create();
    
    public BorrowRepository(UserRepository userRepo){
        this.userRepository = userRepo;
        readRecords();
    }

    public void readRecords(){
        java.io.File file = new java.io.File(filePath);
        if(!file.exists() || file.length() == 0){
            this.borrowedBooks = new HashMap<Integer,BorrowRecord>();
        }

        try(FileReader fileReader = new FileReader(filePath)) {
            java.lang.reflect.Type tipoMapa = new com.google.gson.reflect.TypeToken<HashMap<Integer,BorrowRecord>>(){}.getType();
            HashMap<Integer,BorrowRecord> loadedMap = gson.fromJson(fileReader, tipoMapa);

            if(loadedMap != null){
                this.borrowedBooks = loadedMap;
                this.recordID = Collections.max(borrowedBooks.keySet())  ; 
            }
        } catch(IOException e) {
            System.out.println("Se ha detectado un error de tipo: "+ e.getMessage());
        }
    }

    public void saveRecords(){
        try(FileWriter fileWriter = new FileWriter(filePath)){
            gson.toJson(this.borrowedBooks, fileWriter);            
        } catch (Exception e) {
            System.out.println("Se ha detectado un error de tipo: "+ e.getMessage());
        }
    }

    public void linkUsersToRecords(UserRepository userRepo,BookRepository bookRepo) {
    for (BorrowRecord record : borrowedBooks.values()) {
        if (record.getUser() == null) {
            record.setUser(userRepo.getUser(record.getUserID()));
        }
        if (record.getBook() == null) {
            record.setBook(bookRepo.getBook(record.getBookID()));
        }
    }
}

    //CREATE record
    public void addBorrowRecord(BorrowRecord record){
        for (BorrowRecord bRecord : borrowedBooks.values()) {
        if (bRecord.getBookID() == record.getBookID() && !bRecord.isReturned()) {
            System.out.println("Error: El libro ya tiene un préstamo activo en los registros.");
            return; // No guarda nada
        }
    }
        borrowedBooks.put(recordID++, record);
        saveRecords();
    }

    //READ record
    public BorrowRecord getBorrowedRecord(int id){
        if(id <= 0){
            System.out.println("Record invalido de encontar.");
        }

        return borrowedBooks.get(id);
    }

    //UPDATE record
    public void updateRecord(int id,User user,Book book){
        BorrowRecord record = getBorrowedRecord(id);

        record.setUser(user);
        record.setBook(book);
        saveRecords();
    }

    //DELETE record
    public void deleteRecord(int id){
        borrowedBooks.remove(id);
        saveRecords();
    }

    public Collection<BorrowRecord> getAllRecords(){
        return borrowedBooks.values();
    }

    public Set<Map.Entry<Integer, BorrowRecord>> getAllRecordwithID(){
        return borrowedBooks.entrySet();
    }
}
