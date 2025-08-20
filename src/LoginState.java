//login state covers authentication screen
//has hardcoded admin login values, only 1 owner allowed
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class LoginState implements AppState {
    //gui components
    private JPanel panel;
    private MainFrame context;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginState(MainFrame context) {
        this.context = context;
        initialize();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void initialize() {
        //gui setup
        panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(this::performLogin);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
    }

    private void performLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // check owner credentials
        if (username.equals("admin") && password.equals("admin")) {
            context.changeState(new OwnerStartState(context));
            return;
        }
        // check customer credentials
        for (Customer customer : DataManager.customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                context.changeState(new CustomerStartState(context, customer));
                return;
            }
        }
        // invalid msg
        JOptionPane.showMessageDialog(panel, "Invalid credentials :( ");
    }
}