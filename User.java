import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public abstract class User {

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

    public boolean getBannedUserStatus(){
        return isBanned;
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

    public void printUserBorrowedBookTime(Book book){
        System.out.println(getUserBorrowedBookTime(book));
    }

    //Metodo para calcular la reputacion del usuario
    public int calculateScore(){
        return (onTimeReturns * 10) - (lateReturns * 5) - (lostBooks * 20);
    }

    //Metodo para acutalizar la reputacion del usuario
    public void updateScore(){
        this.score = calculateScore();
    }

    public void updateUserStatus(boolean value){
        this.isBanned = value;
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
    public void requestBook(Library library, Book book){
        library.borrowBook(book, this);
    }

     //Metodo para devolver libros.
    public void returnBook(Book book, Library library){

        //Comprobar si el usuario tiene el libro
        if(book.getBorrowedBookUser() != this){
            System.out.println("Este usuario no tiene el libro");
            return;
        }

        //Calculo de la reputacion de user, aumentara si el user devuelve el libro a tiempo
        //El usuario tambien actualizara el estado sobre cuantos libros ha devuelto. si no cumple el plazo dicho contador aumenta.
        int limitday = 14;

        int days = (int) ChronoUnit.DAYS.between(
                book.getUserBorrowedTime(),
                LocalDate.now()
        );

        if(days > limitday){
            this.addLateReturn();
        } else {
            this.addOnTimeReturn();
        }

        //Si por alguna razon al calcular el score saca menos de 0, el usuario esta baneado de la libreria XD
        this.updateScore();
        if (this.getScore() < 0){
            library.banUserFromLibrary(this);
        }

        book.returnBook();
        this.removeBooksFromInventory(book);
        library.markBookAsReturned(this, book);

        System.out.println(
            "El libro: " + book.getTitle() +
            " ha sido devuelto por " + this.getName()
        );
    }

    //Metodo para agregar un libro al inventario de usuario
    public void addBookToUserInventory( Book book){
        userCurrentInventoryID++;
        userCurrentInventory.put(userCurrentInventoryID, book);
    }

    //Shows how many books the user borrow Overload of methods.

    //Just to get the past inventory with it's respective id and title
    public void booksUserHasBorrowed(){
        for(Map.Entry<Integer,Book> entry : userPastInventory.entrySet()){
            Book book = entry.getValue();
            System.out.println("----------------------------------------------");
            System.out.println(
            "ItemID: " +  entry.getKey() +
            " / Book Title: " +book.getTitle()
            );
            System.out.println("----------------------------------------------");
        }
    }

    //Shows All of the info.
    public void booksUserHasBorrowed(boolean detailed){
        if (detailed == true){
            booksUserHasBorrowed();
        }

        for(Map.Entry<Integer,Book> entry : userPastInventory.entrySet()){
            Book book = entry.getValue();
            book.printAllBookInfo();
        }
    }

    //Eliminate books after being returned
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

    //Just to get the past inventory with it's respective id and title
    public void booksUserIsBorrowing(){
        for(Map.Entry<Integer,Book> entry : userCurrentInventory.entrySet()){
            Book book = entry.getValue();
            System.out.println("----------------------------------------------");
            System.out.println(
            "ItemID: " +  entry.getKey() +
            " / Book Title: " +book.getTitle()
            );
        }
    }
}






