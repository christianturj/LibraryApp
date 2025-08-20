// owner main menu
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OwnerStartState implements AppState {
    private JPanel panel;
    private MainFrame context;

    public OwnerStartState(MainFrame context) {
        this.context = context;
        initialize();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void initialize() {
        panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton booksButton = new JButton("Books");
        JButton customersButton = new JButton("Customers");
        JButton logoutButton = new JButton("Logout");

        booksButton.addActionListener(e -> context.changeState(new OwnerBooksState(context)));
        customersButton.addActionListener(e -> context.changeState(new OwnerCustomersState(context)));
        logoutButton.addActionListener(e -> context.changeState(new LoginState(context)));

        panel.add(booksButton);
        panel.add(customersButton);
        panel.add(logoutButton);
    }
}