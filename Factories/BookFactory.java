package Factories;

import Entities.Book;
import Entities.Book.Genre;

public class BookFactory {
    public Book createBook(int id, String title, String author, Genre genre){
        return new Book(
            id,
            title,
            author,
            genre
        );

    }
}
