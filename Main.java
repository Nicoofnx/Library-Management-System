import java.util.Map;
import java.util.Scanner;

import ConsolePrinter.Printer;
import Entities.Admin;
import Entities.Book;
import Entities.BorrowRecord;
import Entities.Client;
import Entities.User;
import Entities.Book.Genre;
import Repositories.BookRepository;
import Repositories.BorrowRepository;
import Repositories.UserRepository;

import Services.BookService;
import Services.BorrowService;
import Services.UserService;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static UserService userService;
    private static BookRepository bookRepository;
    private static BookService bookService;
    private static BorrowRepository borrowRepository;
    private static BorrowService borrowService;

    public static String askString(String message) {
        System.out.print(message);
        return input.nextLine();
    }

    public static int askInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("¡Meter número válido, cavernícola!");
            return 0;
        }
    }

    public static void main(String[] args) {
        Printer printer = new Printer();
        UserRepository userRepository = new UserRepository();
        userService = new UserService(userRepository);
        bookRepository = new BookRepository();
        
        // EXPLICACIÓN CAVERNÍCOLA: Aquí conectar cables. Si bookRepository no estar vivo, 
        // registro de préstamo dar nulo y romper todo. IA salvar grupo (C gemini se cree habil y se alaga solo).
        borrowRepository = new BorrowRepository(userRepository);
        borrowRepository.linkUsersToRecords(userRepository, bookRepository); 

        bookService = new BookService(bookRepository, userRepository, borrowRepository, printer);
        borrowService = new BorrowService(borrowRepository, userService, bookService, bookRepository, userRepository);

        boolean isRunning = true;
        while (isRunning) {
            User loggedUser = null;
            boolean userLogged = false;
            printer.printWelcome();
            String responseQ1 = input.nextLine();

            // EXPLICACIÓN CAVERNÍCOLA: Usuario dice NO estar registrado. Preguntar si ser jefe (Admin).
            if (responseQ1.equalsIgnoreCase("No")) {
                System.out.println("¿Es administrador?");
                String responseQ2 = askString("Responda con Si/No: ");

                if (responseQ2.equalsIgnoreCase("Si")) {
                    registerAdminUser();
                } else if (responseQ2.equalsIgnoreCase("No")) {
                    registerClientUser();
                }
            } 
            
            // EXPLICACIÓN CAVERNÍCOLA: Usuario dice SÍ tener cuenta. Intentar entrar a la cueva.
            if (responseQ1.equalsIgnoreCase("Si")) {
                loggedUser = loginUser();
                if (loggedUser != null) {
                    userLogged = true;
                }
            }

            // EXPLICACIÓN CAVERNÍCOLA: Si contraseña estar bien, abrir menú según tu tipo de tribu (Admin o Cliente).
            if (userLogged) {
                if (loggedUser instanceof Admin) {
                    adminMenu((Admin) loggedUser);
                } else if (loggedUser instanceof Client) {
                    clientMenu((Client) loggedUser);
                }
            }
        }
    }

    // EXPLICACIÓN CAVERNÍCOLA: Rutina repetida de pedir datos sacada a su propia piedra para no escribir doble.
    private static String[] askUserData(String tipoClient) {
        System.out.println("Entendido usted es un " + tipoClient);
        System.out.println("Ingrese su nombre y apellido");
        String name = askString("Nombre: ");
        String surname = askString("Apellido: ");
        String email = askString("Email: ");
        String password = askString("Contraseña: ");
        return new String[]{name, surname, email, password};
    }

    public static void registerAdminUser() {
        String[] data = askUserData("Administrador");
        userService.addAdminUser(data[0], data[1], data[2], data[3]);
        System.out.println("Registrado con éxito");
    }

    public static void registerClientUser() {
        String[] data = askUserData("Cliente");
        userService.addClientUser(data[0], data[1], data[2], data[3]);
        System.out.println("Registrado con éxito");
    }

    public static void clientMenu(Client userClient) {
        boolean logOut = false;
        while (!logOut) {
            System.out.println("----------------------------------------------");
            System.out.println("¿Qué haremo' hoy " + userClient.getName() + "?");
            String opciones = askString(
                    "1. Pedir prestado un libro \n" +
                    "2. Devolver un libro\n" +
                    "3. Buscar libros \n" +
                    "4. Cerrar sesión \n"
            );

            switch (opciones) {
                case "1": requestBookMenu(userClient); break;
                case "2": returnBookMenu(userClient); break;
                case "3": searchBooksMenu(); break;
                case "4": logOut = true; break;
            }
        }
    }

    public static void adminMenu(Admin userAdmin) {
        boolean logOut = false;
        while (!logOut) {
            System.out.println("----------------------------------------------");
            System.out.println("¿Qué haremo' hoy " + userAdmin.getName() + "?");
            String opciones = askString(
                    "1. Banear/Desbanear un usuario\n" +
                    "2. Añadir/Eliminar un libro\n" +
                    "3. Mostrar los registros de préstamo\n" +
                    "4. Pedir prestado un libro \n" +
                    "5. Buscar libros \n" +
                    "6. Devolver un libro\n" +
                    "7. Cerrar sesión \n"
            );

            switch (opciones) {
                case "1": menuBanUser(); break;
                case "2": elimOrAddBookMenu(); break;
                case "3": showRecordsMenu(); break;
                case "4": requestBookMenu(userAdmin); break;
                case "5": searchBooksMenu(); break;
                case "6": returnBookMenu(userAdmin); break;
                case "7": logOut = true; break;
            }
        }
    }

    public static void menuBanUser() {
        System.out.println("----------------------------------------------");
        System.out.println("Seleccionó des/banear a un usuario");
        String opciones = askString("1. Banear usuario \n2. Desbanear usuario \n");
        int userID = askInt("Adjunte el ID del usuario: ");
        
        if (opciones.equals("1")) {
            userService.banUserFromLibrary(userID);
        } else if (opciones.equals("2")) {
            userService.unbanUserFromLibrary(userID);
        } else {
            System.out.println("Opción inválida.");
        }
        System.out.println("----------------------------------------------");
    }

    public static void elimOrAddBookMenu() {
        System.out.println("----------------------------------------------");
        System.out.println("Seleccionó Añadir/Eliminar un libro");
        String options2 = askString("1. Añadir libro \n 2. Eliminar libro \n");
        System.out.println("----------------------------------------------");
        
        switch (options2) {
            case "1":
                String inputTitleName = askString("Título del libro: ");
                String inputAuthorName = askString("Nombre del autor: ");
                String inputGenreBook = askString("Género del libro: ");
                Genre genre = Genre.valueOf(inputGenreBook.trim().toUpperCase());
                bookService.addBook(inputTitleName, inputAuthorName, genre);
                System.out.println("Se creó el libro con éxito.");
                break;

            case "2":
                System.out.println("Usted seleccionó eliminar un libro");
                Book foundBook = null;
                // EXPLICACIÓN CAVERNÍCOLA: Antes código gritar ERROR antes de buscar. 
                // Ahora primero buscar, si ser nulo recién gritar "No hay libro".
                while (foundBook == null) {
                    String tituloLibroEliminar = askString("Ingrese el título del libro a eliminar: ");
                    foundBook = bookRepository.getBookbyTitle(tituloLibroEliminar);
                    if (foundBook == null) {
                        System.out.println("Error, libro no encontrado, asegúrese de que sea el correcto.");
                    }
                }
                bookRepository.deleteBook(foundBook.getID());
                System.out.println("Libro eliminado con éxito");
                break;
                
            default:
                System.out.println("Opción inválida xd.");
                break;
        }
        System.out.println("----------------------------------------------");
    }

    public static void showRecordsMenu() {
        System.out.println("Has seleccionado mostrar los registros de préstamo");
        for (Map.Entry<Integer, BorrowRecord> recordsEntry : borrowRepository.getAllRecordwithID()) {
            System.out.println("ID Registro: " + recordsEntry.getKey());
            System.out.println(recordsEntry.getValue());
        }
    }

    public static void requestBookMenu(User user) {
        System.out.println("----------------------------------------------");
        System.out.println("Has seleccionado pedir prestado un libro");
        Book requestedBook = null;
        
        // EXPLICACIÓN CAVERNÍCOLA: Mismo bug de arriba arreglado. No gritar error a la primera.
        while (requestedBook == null) {
            String requestBookInput = askString("Escriba el título del libro que quiera solicitar: ");
            requestedBook = borrowService.requestBookByTitle(requestBookInput.trim(), user.getID());
            if (requestedBook == null) {
                System.out.println("No se pudo realizar el préstamo. Verifica que exista o esté disponible.");
            }
        }
        System.out.println("Se ha transferido el libro: " + requestedBook.getTitle() + " a tu inventario.");
        System.out.println("----------------------------------------------");
    }

    public static void searchBooksMenu() {
        System.out.println("----------------------------------------------");
        System.out.println("Has seleccionado buscar libros");
        String options3 = askString("1. Buscar por título\n2. Buscar por género\n3. Buscar por autor \n");
        
        switch (options3) {
            case "1":
                String title = askString("Ingrese el título del libro: ");
                bookService.searchBookByTitle(title.trim());
                break;
            case "2":
                String gender = askString("Ingrese el género del libro: ");
                Genre genre = Genre.valueOf(gender.trim().toUpperCase());
                bookService.searchBooksByGenre(genre);
                break;
            case "3":
                String author = askString("Ingresa el nombre del autor: ");
                bookService.searchBooksByAuthor(author.trim());
                break;
            default:
                System.out.println("Opción inválida xd.");
                break;
        }
        System.out.println("----------------------------------------------");
    }

    public static void returnBookMenu(User user) {
        boolean notReturned = true;
        Book bookToReturn = null;
        System.out.println("----------------------------------------------");
        System.out.println("Has seleccionado devolver el libro");
        user.booksUserIsBorrowing();
        
        while (notReturned) {
            int idOfTheBookToReturnInput = askInt("Escribe el ID del libro que deseas devolver: ");
            bookToReturn = bookRepository.getBook(idOfTheBookToReturnInput);
            
            if (bookToReturn != null) {
                borrowService.returnBook(user, bookToReturn);
                if (!bookToReturn.getStatusBorrowedBook()) {
                    System.out.println("Se ha devuelto el libro: " + bookToReturn.getTitle());
                    notReturned = false;
                }
            } else {
                System.out.println("No se pudo encontrar ese ID de libro en la base de datos.");
            }
        }
        System.out.println("----------------------------------------------");
    }

    // EXPLICACIÓN CAVERNÍCOLA: Método loginUser dejado aquí abajo para mantener orden de la cueva.
    public static User loginUser() {
        User loggedUser = null;
        while (loggedUser == null) {
            String emailLogin = askString("Email: ");
            String passwordLogin = askString("Contraseña: ");

            loggedUser = userService.login(emailLogin, passwordLogin);
            if (loggedUser == null) {
                System.out.println("Credenciales incorrectas");
                String option = askString("1. Intentar nuevamente\n2. Registrarme\n");

                if (option.equals("2")) {
                    String response = askString("¿Es administrador? (Si/No): ");
                    if (response.equalsIgnoreCase("Si")) {
                        registerAdminUser();
                    } else {
                        registerClientUser();
                    }
                    return null;
                }
            }
        }
        System.out.println("Bienvenido " + loggedUser.getName());
        return loggedUser;
    }
}