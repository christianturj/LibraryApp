//data class for books
//made it serializable for data manager
import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}