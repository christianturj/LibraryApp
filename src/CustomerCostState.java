import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomerCostState implements AppState {
    private JPanel panel;
    private MainFrame context;
    private Customer customer;
    private double total;

    public CustomerCostState(MainFrame context, Customer customer, double total) {
        this.context = context;
        this.customer = customer;
        this.total = total;
        initialize();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void initialize() {
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // info
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        infoPanel.add(new JLabel("Total Cost: " + total));
        infoPanel.add(new JLabel("Points: " + customer.getPoints() + 
            ", Status: " + customer.getStatus()));
        panel.add(infoPanel, BorderLayout.CENTER);
        
        // buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton backButton = new JButton("Back to Shopping");
        JButton logoutButton = new JButton("Logout");
        
        backButton.addActionListener(e -> 
            context.changeState(new CustomerStartState(context, customer))
        );
        logoutButton.addActionListener(e -> 
            context.changeState(new LoginState(context))
        );
        
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
}