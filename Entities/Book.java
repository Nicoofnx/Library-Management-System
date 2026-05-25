package Entities;
import java.time.LocalDate;

public class Book {

    //GENERAL ATRIBUTES
    private int id;
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
        SHONEN,
        TECHNOLOGY,
        FICTION
    }


    //Borrow:User
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

    //Setter title
    public void setTitle(String title){
        this.title = title;
    }

        //Setter title
    public void setAuthor(String author){
        this.title = author;
    }

        //Setter title
    public void setGenre(Genre genre){
        this.genre = genre;
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