package Repositories;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Entities.Book;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;

import GSONAdapters.*;

public class BookRepository {

    /** Mapa de libros disponibles */
    private HashMap<Integer, Book> books = new HashMap<>();
    String filePath = "E:/Users/nicol/Desktop/JavaSecondPractice/Data/books.json";

    //JSON almacen
    private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    .setPrettyPrinting()
    .create();

    public BookRepository(){
        readBooks();
    }

    //Deserializador del Books.json, se asegura de que los libros que estuvieron alguna vez en json se mantengan en el map que los guarda.
    public void readBooks(){

    java.io.File file = new java.io.File(filePath);
    
    if (!file.exists() || file.length() == 0) {
        this.books = new HashMap<>();
        System.out.println("Base de datos de usuarios vacía. Iniciando mapa limpio.");
        return; 
    }
        
    try(FileReader fileReader = new FileReader(filePath)){
        //fromGSON atributo de getClass() para ejecutar la logica de conseguir los libros del JSON.
        //Intente con getClass, pero se sugiere ser mas especifico para no convertir los books en mapas genericos :v
        java.lang.reflect.Type tipoMapa = new com.google.gson.reflect.TypeToken<HashMap<Integer, Book>>(){}.getType();
        HashMap<Integer,Book> loadedBooks =  gson.fromJson(fileReader,tipoMapa);
        if (loadedBooks != null) {
                this.books = loadedBooks; // Asignamos los libros cargados al mapa de la RAM
        }

    } catch(IOException e){
            System.out.println("No se pudo encontrar el registro del libro en el JSON: "+ e.getMessage());
        }
    }

    public void saveBooks(){
        try(FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(this.books, fileWriter);
        } catch (IOException e) {
            System.out.println("No se pudo encontrar el registro del libro en el JSON: "+ e.getMessage());
        }
    }

    public void addBook(Book book){
        books.put(book.getID(), book);
        saveBooks();
    }
    
    //READ libros
    public Book getBook(int id){
        if (id == 0 || id < 0){
            System.out.println("Formato invalido.");
            return null;
        }
        
        return books.get(id);
    }

    public Book getBookbyTitle(String title){
        for(Book bookInRepo : getAllBooks()){
            String titleOfBook = bookInRepo.getTitle();
            if (titleOfBook.equalsIgnoreCase(title)) {
                return bookInRepo;                
            }
        }
        return null;
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

        saveBooks();
    }


    //DELETE libros
    public void deleteBook(int id){
        books.remove(id);
        saveBooks();
    }

    public Collection<Book> getAllBooks(){
        return books.values();
    }

    public Set<Map.Entry<Integer,Book>> showBooksWithID(){
        return books.entrySet();
    }

}


