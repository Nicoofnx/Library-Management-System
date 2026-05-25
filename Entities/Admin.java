package Entities;
import java.util.Map;

import ConsolePrinter.Printer;

import Repositories.BookRepository;
import Repositories.BorrowRepository;

public class Admin extends User {
    private Printer printer;
    private BookRepository bookRepository;
    private BorrowRepository borrowRepository;
    private User user;

    private int bookID = 0;


    public Admin(int id, String name, String surname) {
        super(id, name, surname);
    }
    
    //Añade libros a la libreria
    public void addBookToLibrary(String title, String author, Book.Genre genre){
        bookID++;
        Book book = new Book(bookID,title, author, genre);
        bookRepository.addBook(book);
    }

    //Quita libros de la liberia
    public void removeBookFromLibrary(Book bookToRemove){
        for(Map.Entry<Integer,Book> entry : bookRepository.showBooksWithID()){
            int BookIDToRemove = entry.getKey();
            Book bookInList = entry.getValue();
            if(bookInList.equals(bookToRemove)){
                bookRepository.removeBook(BookIDToRemove);
                break;
            }
        }
    }

    
    //Muestra la lista de los libros prestados con su usuario 
    public void showBorrowedBooksList(){
        System.out.println(borrowRepository.getAllRecordwithID());
    }

    public void showAllUsers(){
        printer.printAllUsers();
    }

    //Sistema de BANEOS
    public void banUserFromLibrary(User user){
        user.updateUserStatus(true);
    }
    
    public void unbanUserFromLibrary(User user){
        user.updateUserStatus(false);
    }
}