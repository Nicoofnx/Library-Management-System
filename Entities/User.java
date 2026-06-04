package Entities;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ConsolePrinter.Printer;
import Services.BookService;

public abstract class User {

    //Transient
    private transient Printer printer;
    private transient BookService bookService;

    //Default atributes
    private String name;
    private String surname;
    private String password;
    private String email;
    private int id;

    //User:Book
    private transient int onTimeReturns;
    private transient int lateReturns;
    private transient int lostBooks;
    private int score;

    //User:Library
    private boolean isBanned = false;
    

    public User(int id, String name, String surname,String email, String password){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;

    }

    //Inventario de libros de user.
    HashMap<Integer, Book> userPastInventory = new HashMap<>();
    private transient int userPastInventoryID = 0;

    HashMap<Integer, Book> userCurrentInventory = new HashMap<>();
    private transient int userCurrentInventoryID = 0;

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

    //Getter Email
    public String getEmail(){
        return email;
    }

    //Getter Email
    public String getPassword(){
        return password;
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

    public Map<Integer, Book> getUserCurrentInventoryMap() {
        return this.userCurrentInventory; // Devuelve el HashMap/Map real directo
    }

    public Map<Integer, Book> getUserPastInventoryMap() {
        return this.userPastInventory; // Devuelve el HashMap/Map real directo
    }

    //Getter Si el usuario tiene el libro prestado.
    public boolean hasUserBorrowedBook(Book book){
        return bookService.getBorrowedByUser(book) == this; //gpt xd
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

    //Getter del ID del userPastInventoryID
    public int getUserPastInventoryID(){
        return userPastInventoryID;
    }

    //Getter del ID del userCurrentInventoryID
    public int getUserCurrentInventoryID(){
        return userCurrentInventoryID;
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

    public void displayUserInfo(){
        if (this.printer == null) {
            this.printer = new Printer(); 
        }
    printer.printUserInfo(this);
    }

    //Metodo para agregar un libro al inventario de usuario
    public void addBookToUserInventory(Book book){
        userCurrentInventoryID++;
        if (this.userCurrentInventory == null) {
            this.userCurrentInventory = new HashMap<>();
        }

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
        if (this.printer == null) {
        this.printer = new Printer(); // 
    }
        printer.printBorrowedBooks(this);
    }

    //Muestra la cantidad de libros que el usuario ha pedido prestado alguna vez
    public void booksUserHasBorrowed(boolean detailed){
        if (this.printer == null) {
        this.printer = new Printer(); // 
        }

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
    if (this.printer == null) {
        this.printer = new Printer(); // 
    }
    System.out.println("Esta es la lista de los libros que tienes en tu inventario.");
    printer.printCurrentBorrowedBooks(this);
    }
    

}





