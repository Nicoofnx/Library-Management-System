package Entities;
import java.time.LocalDate;

public class BorrowRecord {

    private transient User user;
    private transient Book book;
    private int userID;
    private int bookID;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    private boolean returned;

    public BorrowRecord(User user, Book book) {
        this.user = user;
        this.book = book;
        this.userID = user.getID();
        this.bookID = book.getID();
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
        this.returned = false;
    }

    //  Marcar devolución
    public void markAsReturned(boolean status) {
        this.returned = status;
        if (status == true) {
            this.returnDate = LocalDate.now();
        }
    }

    // GETTERS

    //Getter user
    public User getUser() {
        return user;
    }

    //Getter userID
    public int getUserID() {
        return this.userID;
    }

    //Getter book
    public Book getBook() {
        return book;
    }

    //Getter userID
    public int getBookID() {
        return this.bookID;
    }

    //Getter fecha de prestamo
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    //Getter fecha de devolucion
    public LocalDate getReturnDate() {
        return returnDate;
    }

    //Getter estado devuelto del libro
    public boolean isReturned() {
        return returned;
    }

    //SETTERS

    //Setter user
    public void setUser(User user){
        this.user = user;
    }

    //Setter book
    public void setBook(Book book){
        this.book = book;
    }

    @Override
    public String toString() {
    String userInfo = (user != null) ? (user.getName() + " " + user.getSurname()) : "USUARIO ELIMINADO";
    
    String bookInfo = (book != null) ? book.getTitle() : "LIBRO NO ENCONTRADO (ID: " + this.bookID + ")";

    return "BorrowRecord {" +
            "User: " + userInfo +
            ", Book: " + bookInfo +
            ", BorrowDate: " + borrowDate +
            ", ReturnDate: " + (returnDate != null ? returnDate : "NOT RETURNED") +
            ", Status: " + (returned ? "RETURNED" : "BORROWED") +
            "}";
}
}