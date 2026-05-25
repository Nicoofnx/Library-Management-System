package Services;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import Entities.Book;
import Entities.User;
import Factories.BorrowFactory;
import Entities.BorrowRecord;

import Repositories.BorrowRepository;

public class BorrowService {

    private BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository,UserService userService,BookService bookService){
        this.borrowRepository = borrowRepository;
        this.userService = userService;
        this.bookService = bookService;
    }
    private UserService userService;
    private BookService bookService;

    /*
    Procesa el préstamo de un libro.
    */
    public void borrowBook(Book book, User user) {
        if (book.getStatusBorrowedBook()) {
            System.out.println("El libro ya está prestado");
            return; //Break from the method.
        }

        if (user.getBannedUserStatus() == true) {
            System.out.println("Usuario sancionado");
            return; //Break from the method.
        }

        book.borrowBook(user);
        addToRecordList(user, book);
        user.addBookToUserInventory(book);

        System.out.println("El libro: " + book.getTitle() +
                " ha sido entregado a " + user.getName());
    }

        /**
    Crea y registra un préstamo en el sistema.
     */
    public BorrowRecord addToRecordList(User user, Book book) {
        BorrowFactory factory = new BorrowFactory();
        BorrowRecord record = factory.createRecord(user, book);

        borrowRepository.addBorrowRecord(record);

        return record;
    }

    public void returnBook(User user,Book book){

    //Comprobar si tan siquiera alguien pidio el libro
    if(!book.getStatusBorrowedBook()){
        System.out.println("El libro no está prestado");
        return;
    }

    //Comprobar si el usuario tiene el libro
    if(book.getBorrowedBookUser() != user){
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
            user.addLateReturn();
        } else {
            user.addOnTimeReturn();
        }

        //Si por alguna razon al calcular el score saca menos de 0, el usuario esta baneado de la libreria XD
        user.updateScore();
        if (user.getScore() < 0){
            userService.banUserFromLibrary(user);
        }

        book.returnBook();
        user.removeBooksFromInventory(book);
        bookService.markBookAsReturned(user, book);

        System.out.println(
            "El libro: " + book.getTitle() +
            " ha sido devuelto por " + user.getName()
        );
    }
}
