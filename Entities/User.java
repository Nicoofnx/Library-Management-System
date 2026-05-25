package Entities;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ConsolePrinter.Printer;
import Services.BorrowService;

public abstract class User {

    //Printer
    private Printer printer;

    //Default atributes
    private String name;
    private String surname;
    private int id;

    //User:Book
    private int onTimeReturns;
    private int lateReturns;
    private int lostBooks;
    private int score;

    //User:Library
    private boolean isBanned = false;
    private BorrowService borrowService;

    public User(int id, String name, String surname){
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    //Inventario de libros de user.
    HashMap<Integer, Book> userPastInventory = new HashMap<>();
    int userPastInventoryID = 0;

    HashMap<Integer, Book> userCurrentInventory = new HashMap<>();
    int userCurrentInventoryID = 0;

    //Getter ID
    public int getID(){
        return id;
    }

    //Getter Name
    public String getName(){
        return name;
    }

    //Getter Surname
    public String getSurname(){
        return surname;
    }

    //Getter Score
    public int getScore(){
        return score;
    }

    //Getter sobre el estado de baneo del usuario
    public boolean getBannedUserStatus(){
        return isBanned;
    }

    //Setter name
    public void setName(String name){
        this.name = name;
    }

    //Setter surname
    public void setSurname(String surname){
        this.surname = surname;
    }

    public Set<Map.Entry<Integer, Book>> getPastInventory(){
        return userPastInventory.entrySet();
    }

    public Set<Map.Entry<Integer, Book>> getCurrentInventory(){
        return userCurrentInventory.entrySet();
    }

    //Getter Si el usuario tiene el libro prestado.
    public boolean hasUserBorrowedBook(Book book){
        return book.getBorrowedBookUser() == this; //gpt xd
    /* Explicación del método:

    El método getBorrowedBookUser() devuelve un objeto de tipo User,
    es decir, el usuario que actualmente tiene prestado el libro.

    El método hasUserBorrowedBook() verifica si el usuario que posee
    el libro es el mismo objeto actual (this).

    Usamos "this" porque estamos dentro de la clase User,
    entonces "this" representa al usuario actual que está ejecutando el método.
    */
    }

    //Getter -- Printer del tiempo que el usuario tuvo el libro prestado.
    public LocalDate getUserBorrowedBookTime(Book book){
        return book.getUserBorrowedTime();
    }

    //Metodo para calcular la reputacion del usuario
    public int calculateScore(){
        return (onTimeReturns * 10) - (lateReturns * 5) - (lostBooks * 20);
    }

    //Metodo para acutalizar la reputacion del usuario
    public void updateScore(){
        this.score = calculateScore();
    }

    public void updateUserStatus(boolean banned){
        this.isBanned = banned;
    }

     //Metodo auxiliar para sacar score
    public void addLateReturn(){
        lateReturns++;
    }

    //Metodo auxiliar para sacar score
    public void addOnTimeReturn(){
        onTimeReturns++;
    }

    //Metodo para enviar una solicitud de pedir libros.
    public void requestBook(Book book){
        borrowService.borrowBook(book, this);
    }

    //Metodo para agregar un libro al inventario de usuario
    public void addBookToUserInventory( Book book){
        userCurrentInventoryID++;
        userCurrentInventory.put(userCurrentInventoryID, book);
    }

    //Elimina libros ldel inventario luego de devolverlos
    public void removeBooksFromInventory(Book bookToRemove){

        for(Map.Entry<Integer, Book> entry : userCurrentInventory.entrySet()){

            int id = entry.getKey();
            Book bookInList = entry.getValue();

            if(bookToRemove.equals(bookInList)){
                userCurrentInventory.remove(id); //If you have key you can remove the value at the same time.
                userPastInventory.put(id, bookInList);
                break; 
            }
        }
    }

    //Sobrecarga de metodos para mostrar la cantidad de libros que ha pedido prestado el usuario

    //Just to get the past inventory with it's respective id and title
    public void booksUserHasBorrowed(){
        printer.printBorrowedBooks(this);
    }

    //Muestra la cantidad de libros que el usuario ha pedido prestado alguna vez
    public void booksUserHasBorrowed(boolean detailed){
        if (detailed == true){
            booksUserHasBorrowed();
        }

        for(Map.Entry<Integer,Book> entry : userPastInventory.entrySet()){
            Book book = entry.getValue();
            printer.printAllBookInfo(book);
        }
    }

    //Solo para conseguir el id y nombre del usuario
    public void booksUserIsBorrowing(){
        printer.printCurrentBorrowedBooks(this);
    }
}






