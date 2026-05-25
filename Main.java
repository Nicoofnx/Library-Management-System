import ConsolePrinter.Printer;
import Entities.Admin;
import Entities.Book;
import Entities.Client;

import Repositories.BookRepository;
import Repositories.BorrowRepository;
import Repositories.UserRepository;

import Services.BookService;
import Services.BorrowService;
import Services.UserService;

public class Main {

    //Debug prop gpt xdxdxd
    public static void main(String[] args) {

        // =========================================
        // PRINTER
        // =========================================

        Printer printer = new Printer();

        // =========================================
        // REPOSITORIES
        // =========================================

        UserRepository userRepository =
                new UserRepository();

        BookRepository bookRepository =
                new BookRepository();

        BorrowRepository borrowRepository =
                new BorrowRepository();

        // =========================================
        // SERVICES
        // =========================================

        UserService userService =
                new UserService(userRepository);

        BookService bookService =
                new BookService(
                        bookRepository,
                        borrowRepository,
                        printer
                );

        BorrowService borrowService =
                new BorrowService(
                        borrowRepository,
                        userService,
                        bookService
                );

        // =========================================
        // USERS
        // =========================================

        Client nico =
                userService.addClientUser(
                        "Nico",
                        "Lopez"
                );

        Admin angel =
                userService.addAdminUser(
                        "Angel",
                        "Sifuentes"
                );

        // =========================================
        // BOOKS
        // =========================================

        Book cleanCode = new Book(
                1,
                "Clean Code",
                "Robert Martin",
                Book.Genre.TECHNOLOGY
        );

        Book nineteen84 = new Book(
                2,
                "1984",
                "George Orwell",
                Book.Genre.FICTION
        );

        Book atomicHabits = new Book(
                3,
                "Atomic Habits",
                "James Clear",
                Book.Genre.PERIODISM
        );

        bookRepository.addBook(cleanCode);
        bookRepository.addBook(nineteen84);
        bookRepository.addBook(atomicHabits);

        // =========================================
        // SEARCH TESTS
        // =========================================

        System.out.println(
                "\n========== SEARCH BY TITLE ==========\n"
        );

        bookService.searchBookByTitle("1984");

        System.out.println(
                "\n========== SEARCH BY GENRE ==========\n"
        );

        bookService.searchBooksByGenre(
                Book.Genre.TECHNOLOGY
        );

        // =========================================
        // BORROW TEST
        // =========================================

        System.out.println(
                "\n========== BORROW BOOK ==========\n"
        );

        borrowService.borrowBook(cleanCode, nico);

        borrowService.borrowBook(atomicHabits, nico);

        // =========================================
        // USER INVENTORY
        // =========================================

        System.out.println(
                "\n========== USER INVENTORY ==========\n"
        );

        printer.printBorrowedBooks(nico);

        // =========================================
        // BORROW HISTORY
        // =========================================

        System.out.println(
                "\n========== BORROW RECORDS ==========\n"
        );

        bookService.showBorrowedBooksList();

        // =========================================
        // RETURN BOOK
        // =========================================

        System.out.println(
                "\n========== RETURN BOOK ==========\n"
        );

        borrowService.returnBook(
                nico,
                cleanCode
        );

        // =========================================
        // INVENTORY AFTER RETURN
        // =========================================

        System.out.println(
                "\n========== INVENTORY AFTER RETURN ==========\n"
        );

        printer.printBorrowedBooks(nico);
        angel.banUserFromLibrary(nico);
        printer.printBannedUser(nico);

        printer.printUserBannedStatus(nico);
    }
}