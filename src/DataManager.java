// main data manager
// decided to use java serialization over json/xml for simplicity, avoids having to implement a parser...
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static List<Book> books = new ArrayList<>();
    public static List<Customer> customers = new ArrayList<>();

    public static void saveData() {
        try (ObjectOutputStream oosBooks = new ObjectOutputStream(new FileOutputStream("books.txt"));
             ObjectOutputStream oosCustomers = new ObjectOutputStream(new FileOutputStream("customers.txt"))) {
            //serialize array lst
            oosBooks.writeObject(new ArrayList<>(books));
            oosCustomers.writeObject(new ArrayList<>(customers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        try (ObjectInputStream oisBooks = new ObjectInputStream(new FileInputStream("books.txt"));
             ObjectInputStream oisCustomers = new ObjectInputStream(new FileInputStream("customers.txt"))) {
            
            books = (List<Book>) oisBooks.readObject();
            customers = (List<Customer>) oisCustomers.readObject();
            
            // validate array is modifiable
            if (!(books instanceof ArrayList)) {
                books = new ArrayList<>(books);
            }
            if (!(customers instanceof ArrayList)) {
                customers = new ArrayList<>(customers);
            }
            // if empty, creates new one
        } catch (IOException | ClassNotFoundException e) {
            books = new ArrayList<>();
            customers = new ArrayList<>();
        }
    }
}