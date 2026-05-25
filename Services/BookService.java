package Services;
import java.util.Map;

import ConsolePrinter.*;

import Entities.Book;
import Entities.BorrowRecord;
import Entities.User;

import Repositories.BookRepository;
import Repositories.BorrowRepository;

/*
 * Representa el sistema de una biblioteca.
 * Maneja solo lo relacionado a libros y el historial de préstamos.
*/

public class BookService {

private BookRepository bookRepository;
private Printer printer;
    public BookService(BookRepository bookRepository,BorrowRepository borrowRepository,Printer printer){
        this.bookRepository = bookRepository;
        this.borrowRepository = borrowRepository;
        this.printer = printer;
    }

private BorrowRepository borrowRepository;

    /*
    Marca un préstamo como devuelto dentro del historial.
    Busca el registro correspondiente y lo actualiza.
     */
    public void markBookAsReturned(User user, Book book) {
        
        for (BorrowRecord record : borrowRepository.getAllRecords()) {
                if (record.getUser().equals(user)
                        && record.getBook().equals(book)
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
                System.out.println("Libro encontrado: " + book.getTitle());
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
}
