import java.util.HashMap;
import java.util.Map;

/**
 * Clase Library.
 * Representa el sistema de una biblioteca.
 * Maneja usuarios, libros y el historial de préstamos.
 */
public class Library {

    /** Mapa de usuarios registrados */
    HashMap<Integer, User> usersList = new HashMap<>();
    int userID = 0;

    /*
    Registra un nuevo usuario en la biblioteca.
     */
    public Client addClientUser(String name, String surname) {
        userID++;

        Client clientUser = new Client(userID, name, surname);
        usersList.put(userID, clientUser);

        return clientUser;
    }

    /*
    Registra un nuevo usuario en la biblioteca.
     */
    public Admin addAdminUser(String name, String surname) {
        userID++;

        Admin adminUser = new Admin(userID, name, surname);
        usersList.put(userID, adminUser);

        return adminUser;
    }

    /** Mapa de libros disponibles */
    HashMap<Integer, Book> bookList = new HashMap<>();
    int bookID = 0;

    /*
    Agrega un libro al sistema.
     */
    public Book addBook(String title, String author, Book.Genre genre) {
        bookID++;

        Book book = new Book(bookID, title, author, genre);
        bookList.put(bookID, book);

        return book;
    }

    /** Historial de préstamos de la biblioteca */
    HashMap<Integer, BorrowRecord> borrowedBooksList = new HashMap<>();
    int borrowBookID = 0;

    /**
    Crea y registra un préstamo en el sistema.
     */
    public BorrowRecord addToRecordList(User user, Book book) {
        BorrowRecord borrowRecord = new BorrowRecord(user, book);

        borrowBookID++;
        borrowedBooksList.put(borrowBookID, borrowRecord);

        return borrowRecord;
    }

    /*
    Marca un préstamo como devuelto dentro del historial.
    Busca el registro correspondiente y lo actualiza.
     */
    public void markBookAsReturned(User user, Book book) {
        for (BorrowRecord record : borrowedBooksList.values()) {
                if (record.getUser().equals(user)
                        && record.getBook().equals(book)
                        && !record.isReturned()) {

                    record.markAsReturned(true);
                    break;
            }
        }
    }

    /*Ento
    Procesa el préstamo de un libro.
    Valida disponibilidad y reputación del usuario antes de asignarlo.
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

    /*
    Busca un libro por su ID.
     */
    public void searchBookByID(int ID) {
        Book book = bookList.get(ID);

        if (book == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        System.out.println("----------------------------------------------");
        System.out.println(
                "Libro encontrado:" +
                        "\nID: " + book.getID() +
                        "\nTitulo: " + book.getTitle() +
                        "\nAutor: " + book.getAuthor() +
                        "\nGenero: " + book.getGenre()
        );
        System.out.println("----------------------------Ve------------------");
    }

    /*
    Busca libros por título.
     */
    public void searchBookByTitle(String title) {

        boolean found = false;

        for (Map.Entry<Integer, Book> entry : bookList.entrySet()) {
            Book book = entry.getValue();
            
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.printAllBookInfo(true);
                found = true;
                break;
            }
        }

        if (!found) {
                System.out.println("Libro no encontrado");
            }
    }

    /*
    Busca libros por género.
     */
    public void searchBooksByGenre(Book.Genre genre) {
        boolean found = false;
        for (Map.Entry<Integer, Book> entry : bookList.entrySet()) {
            Book book = entry.getValue();

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

    /*
    Muestra el historial completo de préstamos.
     */
    public void showBorrowedBooksList() {
        for (Map.Entry<Integer, BorrowRecord> entry : borrowedBooksList.entrySet()) {
            System.out.println(
                    "Id: " + entry.getKey() +
                    " Usuario con libros prestados: " + entry.getValue()
            );
        }
    }

    public void banUserFromLibrary(User user){
        user.updateUserStatus(true);
    }

    public void unbanUserFromLibrary(User user){
        user.updateUserStatus(false);
    }

    /*
    Resumen del estado actual de la biblioteca.
     */
    @Override
    public String toString() {
        return "Library {" +
                "\nUsers: " + usersList.size() +
                "\nBooks: " + bookList.size() +
                "\nBorrow Records: " + borrowedBooksList.size() +
                "\n}";
    }
}
