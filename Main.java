public class Main {
    public static void main(String[] args){


        Library library = new Library();

        // ================= USERS =================
        Client nico = library.addClientUser(
            "Nicolai",
            "Juarez"
        );

        Client maria = library.addClientUser(
            "Maria",
            "Lopez"
        );

        Admin angel = library.addAdminUser(
            "Angel",
            "Sifuentes"
        );

        // ================= BOOKS =================
        Book dracula = library.addBook(
            "Dracula",
            "Bram Stoker",
            Book.Genre.HORROR
        );

        Book sapiens = library.addBook(
            "Sapiens",
            "Yuval Noah Harari",
            Book.Genre.HISTORY
        );

        Book meditations = library.addBook(
            "Meditations",
            "Marcus Aurelius",
            Book.Genre.PHILOSOPHY
        );

        Book metamorphosis = library.addBook(
            "The Metamorphosis",
            "Franz Kafka",
            Book.Genre.DRAMA
        );

        Book onePiece = library.addBook(
            "One Piece",
            "Eiichiro Oda",
            Book.Genre.SHONEN
        );

        // ================= ADMIN TEST =================
        System.out.println("\n========== ADMIN TEST ==========");

        angel.addBookToLibrary(
            library,
            "No Longer Human",
            "Osamu Dazai",
            Book.Genre.PSYCHOLOGY
        );

        angel.showAllUsers(library);

        // ================= BORROW TEST =================
        System.out.println("\n========== BORROW TEST ==========");

        nico.requestBook(library, onePiece);
        nico.requestBook(library, dracula);

        maria.requestBook(library, meditations);

        angel.requestBook(library, sapiens);

        // ================= RETURN TEST =================
        System.out.println("\n========== RETURN TEST ==========");

        nico.returnBook(onePiece, library);

        // ================= CURRENT INVENTORY =================
        System.out.println("\n========== CURRENT INVENTORY ==========");

        System.out.println("\n--- NICO CURRENT INVENTORY ---");
        nico.booksUserIsBorrowing();

        System.out.println("\n--- MARIA CURRENT INVENTORY ---");
        maria.booksUserIsBorrowing();

        // ================= PAST INVENTORY =================
        System.out.println("\n========== PAST INVENTORY ==========");

        System.out.println("\n--- NICO PAST INVENTORY ---");
        nico.booksUserHasBorrowed();

        // ================= SEARCH TEST =================
        System.out.println("\n========== SEARCH TEST ==========");

        System.out.println("\n--- SEARCH BY ID ---");
        library.searchBookByID(1);

        System.out.println("\n--- SEARCH BY TITLE ---");
        library.searchBookByTitle("Dracula");

        System.out.println("\n--- SEARCH BY GENRE ---");
        library.searchBooksByGenre(Book.Genre.HORROR);

        // ================= BORROW HISTORY =================
        System.out.println("\n========== BORROW HISTORY ==========");

        library.showBorrowedBooksList();

        // ================= REMOVE BOOK TEST =================
        System.out.println("\n========== REMOVE BOOK TEST ==========");

        angel.removeBookFromLibrary(library, metamorphosis);

        System.out.println("\n--- SEARCH REMOVED BOOK ---");
        library.searchBookByTitle("The Metamorphosis");

        // ================= SYSTEM STATUS =================
        System.out.println("\n========== LIBRARY STATUS ==========");

        System.out.println(library);
    }
}
