package Services;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import Entities.Book;
import Entities.User;
import Factories.BorrowFactory;
import Entities.BorrowRecord;
import Repositories.BookRepository;
import Repositories.BorrowRepository;
import Repositories.UserRepository;

public class BorrowService {

    private BookRepository bookRepository;
    private BorrowRepository borrowRepository;
    private UserService userService;
    private BookService bookService;
    private UserRepository userRepository;

    public BorrowService(BorrowRepository borrowRepository,UserService userService,BookService bookService,BookRepository bookRepository, UserRepository userRepository){
        this.borrowRepository = borrowRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    //Metodo para enviar una solicitud de pedir libros.
    public Book requestBookByTitle(String title,int userID){
        User user = userRepository.getUser(userID);

        if (user == null) {
            System.out.println("Usuario no encontrado.");
            return null;
        }

        for(Book book : bookRepository.getAllBooks())
            if(book.getTitle().equalsIgnoreCase(title)){
                if(book.getStatusBorrowedBook()){
                    System.out.println("El libro ya está prestado actualmente.");
                    return null; 
                }
                borrowBook(book, user);
                userRepository.updateUser(userID);
                return book;
            }

        return null;
    }

    /*
    Procesa el préstamo de un libro.
    */
    public void borrowBook(Book book, User user) {
        if (book.getStatusBorrowedBook()) {
            System.out.println("El libro ya está prestado");
            return; //Break from the method.
        }

        if (user.getBannedUserStatus() == true) {
            System.out.println("Un usuario sancionado no puede solicitar libros");
            return; //Break from the method.
        }

        BorrowRecord newRecord = addRecord(user, book);
        book.borrowBook(user);
        borrowRepository.addBorrowRecord(newRecord);
        bookService.updateBookBorrowedByUserID(book, user);
        user.addBookToUserInventory(book);
        bookRepository.saveBooks();
        userRepository.updateUser(user.getID());

        System.out.println("El libro: " + book.getTitle() +
                " ha sido entregado a " + user.getName());
    }

        /**
    Crea y registra un préstamo en el sistema.
     */
    public BorrowRecord addRecord(User user, Book book) {
        BorrowFactory factory = new BorrowFactory();
        BorrowRecord record = factory.createRecord(user, book);
        return record;
    }

    public void returnBook(User user,Book book){

        if (book.getUserBorrowedTime() == null) {
        System.out.println(" El libro no tiene una fecha de préstamo registrada.");
        return; 
        }

        //Comprobar si tan siquiera alguien pidio el libro
        if(!book.getStatusBorrowedBook()){
            System.out.println("El libro no está prestado");
            return;
        }

        //Comprobar si el usuario tiene el libro
        if(bookService.getBorrowedByUser(book) != user){
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
            userService.banUserFromLibrary(user.getID());
        }

        book.returnBook();
        user.removeBooksFromInventory(book);
        bookService.markBookAsReturned(user, book);
        bookRepository.saveBooks();
        borrowRepository.saveRecords();
        userRepository.updateUser(user.getID());

        System.out.println(
            "El libro: " + book.getTitle() +
            " ha sido devuelto por " + user.getName()
        );
    }
}
