import java.util.Map;

public class Admin extends User {
    public Admin(int id, String name, String surname) {
        super(id, name, surname);
    }
    
    public void addBookToLibrary(Library library,String title, String author, Book.Genre genre){
        library.addBook(title,author,genre);
    }

    public void removeBookFromLibrary(Library library, Book bookToRemove){
        for(Map.Entry<Integer,Book> entry : library.bookList.entrySet()){
            int BookIDToRemove = entry.getKey();
            Book bookInList = entry.getValue();
            if(bookInList.equals(bookToRemove)){
                library.bookList.remove(BookIDToRemove);
                break;
            }
        }
    }

    public void showBorrowedBooksList(Library library){
        library.showBorrowedBooksList();
    }

    public void showAllUsers(Library library){
        for(Map.Entry<Integer,User> entry : library.usersList.entrySet()){
            int usuarioID = entry.getKey();
            User usuario = entry.getValue();

            System.out.println("----------------------------------------------");
            System.out.println("ID: " + usuarioID + " | " + "Nombre: " +usuario.getName() + " | " + usuario.getClass().getSimpleName());
            // usuario.getClass().getSimpleName() - Printea Admin o Usuario, datocurioson xd
            System.out.println("----------------------------------------------");
        }
    }

    public void banUserFromLibrary(Library library , User user){
        library.banUserFromLibrary(user);
    }
    
    public void unbanUserFromLibrary(Library library , User user){
        library.unbanUserFromLibrary(user);
    }
}