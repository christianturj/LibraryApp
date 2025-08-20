import java.io.Serializable;

public class Customer implements Serializable {
    private String username;
    private String password;
    private int points;

    public Customer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    
    public String getStatus() {
        return points >= 1000 ? "Gold" : "Silver";
    }
}