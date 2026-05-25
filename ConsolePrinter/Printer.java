package ConsolePrinter;
import Repositories.*;
import Entities.*;

import java.util.*;


public class Printer {

    private UserRepository userRepository;
    private BookRepository repositoryBooks;
    //Printea la info general del libro.
    public void printAllBookInfo(Book book){

    System.out.println("----------------------------------------------");
    System.out.println(
        "ID Libro: " + book.getID()
        + "\nTitulo: " + book.getTitle()
        + "\nAutor: " + book.getAuthor()
        + "\nGenero: " + book.getGenre()
        + "\nPrestado: " + (book.getStatusBorrowedBook() ? "Si" : "No")
    );

    if(book.getStatusBorrowedBook() && book.getBorrowedBookUser() != null){

        System.out.println(
            "Prestado a: " + book.getBorrowedBookUser().getName()
            + "\nTiempo de prestamo: " + book.getUserBorrowedTime()
        );
    }
    System.out.println("----------------------------------------------");
    }

    //Printea la info general del libro.
    public void printAllBookInfo(Book book,boolean isForUser){
    if (isForUser == false){
        printAllBookInfo(book);
    }

    System.out.println("----------------------------------------------");
    System.out.println(
        "ID Libro: " + book.getID()
        + "\nTitulo: " + book.getTitle()
        + "\nAutor: " + book.getAuthor()
        + "\nGenero: " + book.getTitle()
    );
    System.out.println("----------------------------------------------");
    }

    public void printBorrowedBooks(User user){
    for(Map.Entry<Integer,Book> entry : user.getPastInventory()){
            Book book = entry.getValue();
            System.out.println("----------------------------------------------");
            System.out.println(
            "ItemID: " +  entry.getKey() +
            " / Book Title: " +book.getTitle()
            );
            System.out.println("----------------------------------------------");
        }
    }

    public void printAllUsers(){
    for(Map.Entry<Integer,User> entry : userRepository.showUsersWithID()){
            int usuarioID = entry.getKey();
            User usuario = entry.getValue();

            System.out.println("----------------------------------------------");
            System.out.println("ID: " + usuarioID + " | " + "Nombre: " +usuario.getName() + " | " + usuario.getClass().getSimpleName());
            // usuario.getClass().getSimpleName() - Printea Admin o Usuario, datocurioson xd
            System.out.println("----------------------------------------------");
        }
    }

    public void printCurrentBorrowedBooks(User user){
        for(Map.Entry<Integer,Book> entry : user.getCurrentInventory()){
                Book book = entry.getValue();
                System.out.println("----------------------------------------------");
                System.out.println(
                "ItemID: " +  entry.getKey() +
                " / Book Title: " +book.getTitle()
                );
        }
    }   

    public void printFoundBookByID(int ID){
    Book book = repositoryBooks.getBook(ID);
    System.out.println("----------------------------------------------");
        System.out.println(
                "Libro encontrado:" +
                        "\nID: " + book.getID() +
                        "\nTitulo: " + book.getTitle() +
                        "\nAutor: " + book.getAuthor() +
                        "\nGenero: " + book.getGenre()
        );
        System.out.println("------------------------------------------------");
    }

    public void printBannedUser(User user){
        System.out.println("----------------------------------------------");
        System.out.println("Se ha baneado al usuario: "+ user.getName());
        System.out.println("----------------------------------------------");
    }

    public void printUnbanBannedUser(User user){
        System.out.println("----------------------------------------------");
        System.out.println("Se ha desbaneado al usuario: "+ user.getName());
        System.out.println("----------------------------------------------");
    }

    public void printUserBorrowedBookTime(User user,Book book){
        System.out.println(user.getUserBorrowedBookTime(book));
    }

    public void printUserBannedStatus(User user){
        System.out.println(user.getBannedUserStatus());
    }
}   