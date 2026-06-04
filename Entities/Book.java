package Entities;
import java.time.LocalDate;

public class Book {

    //GENERAL ATRIBUTES
    private int id;
    private String title;
    private String author;
    private Genre genre;

    public enum Genre {
    //Me quede sin ideas, gracias crisol XD
    CIENCIAFICCION,
    ROMANCE,
    TERROR,          
    FILOSOFIA,
    HISTORIA,
    PSICOLOGIA,
    PERIODISMO,     
    SOCIOLOGIA,
    DRAMA,
    CUENTO,          
    FABULA,
    COMEDIA,
    SHONEN,          
    TECNOLOGIA,
    FICCION
}

    //Borrow:User
    private int borrowedByUserID;
    private boolean isBorrowed;
    private LocalDate timeBorrowedBook;

    public Book(int id, String title, String author, Genre genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;

        this.borrowedByUserID =  0;
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

    //Getter del id del usuario que tomo libro prestado
    public int getBorrowedByUserID(){
        return borrowedByUserID;
    }

    //Setter title
    public void setTitle(String title){
        this.title = title;
    }

    //Setter Author
    public void setAuthor(String author){
        this.title = author;
    }

    //Setter Genre
    public void setGenre(Genre genre){
        this.genre = genre;
    }

    public void setBorrowedByUserID(int userID){
        this.borrowedByUserID = userID;
    }



    public void borrowBook(User user){
        this.borrowedByUserID = user.getID();
        this.isBorrowed = true;
        this.timeBorrowedBook = LocalDate.now();
        
    }

    public void returnBook(){
        this.borrowedByUserID = 0;
        this.isBorrowed = false;
        this.timeBorrowedBook = null;
    }

    @Override
    public String toString() {
        return "Titulo: " + title + "\n" +
            " Autor: " + author +  "\n" +
            " Genero: " + genre;
    }
}