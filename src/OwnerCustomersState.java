//customer management screen
// uses array list check to have modifiable list

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class OwnerCustomersState implements AppState {
    private JPanel panel;
    private MainFrame context;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField usernameField;
    private JTextField passwordField;

    public OwnerCustomersState(MainFrame context) {
        this.context = context;
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
        
        // initialize with modifiable arraylist if null
        if (DataManager.customers == null) {
            DataManager.customers = new ArrayList<>();
        }
        
        // top panel
        tableModel = new DefaultTableModel(new Object[]{"Username", "Password", "Points"}, 0);
        table = new JTable(tableModel);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // middle panel with add controls
        JPanel middlePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        middlePanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        middlePanel.add(usernameField);
        middlePanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        middlePanel.add(passwordField);
        
        // bottom panel w/ buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add Customer");
        JButton deleteButton = new JButton("Delete Selected");
        JButton backButton = new JButton("Back to Main");
        
        addButton.addActionListener(this::addCustomer);
        deleteButton.addActionListener(this::deleteCustomer);
        backButton.addActionListener(e -> context.changeState(new OwnerStartState(context)));
        
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);
        
        panel.add(middlePanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }
    //in sync w/ datamanager
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Customer customer : DataManager.customers) {
            tableModel.addRow(new Object[]{customer.getUsername(), customer.getPassword(), customer.getPoints()});
        }
    }

    private void addCustomer(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        //check if empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Username/password cannot be empty");
            return;
        }
        
        // Check if username already exists
        for (Customer c : DataManager.customers) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(panel, "Username already taken :( ");
                return;
            }
        }
        
        // addd new customer
        DataManager.customers.add(new Customer(username, password, 0));
        refreshTable();
        usernameField.setText("");
        passwordField.setText("");
    }

    private void deleteCustomer(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0 && row < DataManager.customers.size()) {
            DataManager.customers.remove(row);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(panel, "Please select a customer to delete");
        }
    }
}