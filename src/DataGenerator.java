// initial data creation
//only need to use if books.txt and customers.txt is empty
//took it out of main application as only needs to be used once
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class DataGenerator {
    public static void main(String[] args) {
        // example books
        List<Book> books = Arrays.asList(
            new Book("Calculus 3", 12.99),
            new Book("Java Intro", 10.50),
            new Book("Physics", 8.99),
            new Book("Star Trek", 9.75),
            new Book("The Hobbit", 14.25),
            new Book("The Catcher in the Rye", 11.00),
            new Book("Star Wars", 9.50),
            new Book("Moby Dick", 13.25),
            new Book("One Piece", 15.99),
            new Book("Pokemon", 10.00)
        );

        // example customers
        List<Customer> customers = Arrays.asList(
            new Customer("Bob", "password123", 250),
            new Customer("Jack", "456", 1200),
            new Customer("book_lover", "readmore789", 50),
            new Customer("book_fan", "books4life", 850),
            new Customer("reading_lover", "pageTurner", 300)
        );

        // save to files
       try (ObjectOutputStream oosBooks = new ObjectOutputStream(new FileOutputStream("books.txt"));
             ObjectOutputStream oosCustomers = new ObjectOutputStream(new FileOutputStream("customers.txt"))) {
            
            oosBooks.writeObject(books);
            oosCustomers.writeObject(customers);
            System.out.println("Data files created successfully!");
        } catch (IOException e) {
            System.err.println("Error creating files: " + e.getMessage());
        }
    }
}