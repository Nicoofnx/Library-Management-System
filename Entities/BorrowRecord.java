package Entities;
import java.time.LocalDate;

public class BorrowRecord {

    private User user;
    private Book book;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    private boolean returned;

    public BorrowRecord(User user, Book book) {
        this.user = user;
        this.book = book;
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

    //Getter book
    public Book getBook() {
        return book;
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
        return "BorrowRecord {" +
                "User: " + user.getName() + " " + user.getSurname() +
                ", Book: " + book.getTitle() +
                ", BorrowDate: " + borrowDate +
                ", ReturnDate: " + (returnDate != null ? returnDate : "NOT RETURNED") +
                ", Status: " + (returned ? "RETURNED" : "BORROWED") +
                "}";
    }
}