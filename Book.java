import java.time.LocalDate;

public class Book {

    private String title;
    private String author;
    private Genre genre;

    public enum Genre{
        SCIENCEFICTION,
        ROMANCE,
        HORROR,
        PHILOSOPHY,
        HISTORY,
        PSYCHOLOGY,
        PERIODISM,
        SOCIOLOGY,
        DRAMA,
        STORY,
        FABLE,
        COMEDY,
        SHONEN
    }

    private int id;

    private User borrowedBy;

    private boolean isBorrowed;
    private LocalDate timeBorrowedBook;

    public Book(int id, String title, String author, Genre genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;

        this.isBorrowed = false;
        this.timeBorrowedBook = null;
    }

    //Getter ID
    public int getID(){
        return id;
    }

    //Getter Title
    public String getTitle(){
        return title;
    }

    //Getter Genre
    public Genre getGenre(){
        return genre;
    }

    //Getter Status si el libro ha sido prestado
    public boolean getStatusBorrowedBook(){
        return isBorrowed;
    }

    //Getter autor
    public String getAuthor(){
        return author;
    }

    //Getter del tiempo que el usuario tuvo el libro prestado.
    public LocalDate getUserBorrowedTime(){
        return timeBorrowedBook;
    }

    //Getter para conseguir el usuario al que se le presto el libro.
    public User getBorrowedBookUser(){
        return borrowedBy;
    }

    //Printea la info general del libro.
    public void printAllBookInfo(){

    System.out.println("----------------------------------------------");
    System.out.println(
        "ID Libro: " + id
        + "\nTitulo: " + title
        + "\nAutor: " + author
        + "\nGenero: " + genre
        + "\nPrestado: " + (isBorrowed ? "Si" : "No")
    );

    if(isBorrowed && borrowedBy != null){

        System.out.println(
            "Prestado a: " + borrowedBy.getName()
            + "\nFecha de prestamo: " + timeBorrowedBook
        );
    }
    System.out.println("----------------------------------------------");
    }

    //Printea la info general del libro.
    public void printAllBookInfo(boolean isForUser){
    if (isForUser == false){
        printAllBookInfo();
    }

    System.out.println("----------------------------------------------");
    System.out.println(
        "ID Libro: " + id
        + "\nTitulo: " + title
        + "\nAutor: " + author
        + "\nGenero: " + genre
    );
    }


    public void borrowBook(User user){
        this.isBorrowed = true;
        this.borrowedBy = user;
        this.timeBorrowedBook = LocalDate.now();
    }

    public void returnBook(){
        this.isBorrowed = false;
        this.borrowedBy = null;
        this.timeBorrowedBook = null;
    }

    @Override
    public String toString() {
        return "Titulo: " + title +
            ", Autor: " + author +
            ", Genero: " + genre;
    }
}