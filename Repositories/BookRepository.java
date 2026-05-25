package Repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Entities.Book;

public class BookRepository {

    /** Mapa de libros disponibles */
    private HashMap<Integer, Book> books = new HashMap<>();

    //CREATE libros
    public void addBook(Book book){
        books.put(book.getID(), book);
    }
    
    public Book getBook(int id){
        if (id == 0){
            System.out.println("El libro no existe");
        }

        return books.get(id);
    }

    //READ libros
    public void removeBook(int id){
        books.remove(id);
    }

    //UPDATE libros
    public void updateBook(int id, String title, String author, Book.Genre genre){
        Book book = getBook(id);

        if(book == null){
            System.out.println("Libro no encontrado");
            return;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
    }

    //DELETE libros
    public void deleteBook(int id){
        books.remove(id);
    }

    public Collection<Book> getAllBooks(){
        return books.values();
    }

    public Set<Map.Entry<Integer,Book>> showBooksWithID(){
        return books.entrySet();
    }

}


