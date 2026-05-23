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
    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
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