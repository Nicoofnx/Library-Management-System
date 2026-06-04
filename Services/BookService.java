package Services;
import java.util.Map;

import ConsolePrinter.*;

import Entities.Book;
import Entities.Book.Genre;
import Factories.BookFactory;
import Entities.BorrowRecord;
import Entities.User;

import Repositories.BookRepository;
import Repositories.BorrowRepository;
import Repositories.UserRepository;

/*
 * Representa el sistema de una biblioteca.
 * Maneja solo lo relacionado a libros y el historial de préstamos.
*/

public class BookService {

    private UserRepository userRepository;
    private BookRepository bookRepository;
    private BorrowRepository borrowRepository;
    private Printer printer;
    public BookService(BookRepository bookRepository,UserRepository userRepository,BorrowRepository borrowRepository,Printer printer){
        this.bookRepository = bookRepository;
        this.borrowRepository = borrowRepository;
        this.printer = printer;
        this.userRepository = userRepository;
    }

    public Book addBook(String title, String author, Genre genre){
        for (Book b : bookRepository.getAllBooks()) { 
            if (b.getTitle().equalsIgnoreCase(title) && b.getAuthor().equalsIgnoreCase(author) &&  b.getGenre().equals(genre)) {
                System.out.println("El libro: " + b.getTitle() + " ya está registrado.");
                return (Book) b;
            }
        }

        int nuevoID = bookRepository.getAllBooks().size() + 1;
        BookFactory bookFactory = new BookFactory();
        Book book = bookFactory.createBook(nuevoID, title, author, genre);
        bookRepository.addBook(book);
        return book;
    }



    /*
    Marca un préstamo como devuelto dentro del historial.
    Busca el registro correspondiente y lo actualiza.
     */
    public void markBookAsReturned(User user, Book book) {
        for (BorrowRecord record : borrowRepository.getAllRecords()) {
            
            if (record.getUserID() == user.getID() 
                    && record.getBookID() == book.getID() 
                    && !record.isReturned()) {

                record.markAsReturned(true);
                break;
            }
        }
    }

    //Busca un libro por su ID.
    public void searchBookByID(int ID) {
        Book book = bookRepository.getBook(ID);

        if (book == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        printer.printFoundBookByID(ID);
    }

    
    //Busca libros por título.
    
    public void searchBookByTitle(String title) {

        boolean found = false;

        for (Book book : bookRepository.getAllBooks()) {
            
            if (book.getTitle().equalsIgnoreCase(title)) {
                printer.printAllBookInfo(book, true);
                found = true;
                break;
            }
        }

        if (!found) {
                System.out.println("Libro no encontrado");
            }
    }

    
    //Busca libros por género.
    
    public void searchBooksByGenre(Book.Genre genre) {
        boolean found = false;
        for (Book book : bookRepository.getAllBooks()) {

        if (book.getGenre() == genre) {
                printer.printAllBookInfo(book, true);
                found = true;
                break;
            }
        }

        if (!found) {
                System.out.println("Libro/s no encontrado/s");
            }
    }

    public void searchBooksByAuthor(String author) {
        boolean found = false;
        for (Book book : bookRepository.getAllBooks()) {

        if (book.getAuthor() == author) {
                printer.printAllBookInfo(book, true);
                found = true;
                break;
            }
        }

        if (!found) {
                System.out.println("Libro/s no encontrado/s");
            }
    }
    
    //Muestra el historial completo de préstamos.
    
    public void showBorrowedBooksList() {
        for (Map.Entry<Integer, BorrowRecord> entry : borrowRepository.getAllRecordwithID()) {
            System.out.println(
                    "Id: " + entry.getKey() +
                    " Usuario con libros prestados: " + entry.getValue()
            );
        }
    }

    public void updateBookBorrowedByUserID(Book book, User user) {
        if (book != null && user != null) {
            book.setBorrowedByUserID(user.getID());
        }
    }

    public User getBorrowedByUser(Book book) {
        int userID = book.getBorrowedByUserID();

        if ( userID == 0 || userID < 0) {
            System.out.println("No existe un usuario o formato invalido");
            return null;            
        }

        return userRepository.getUser(userID);
    }

}
