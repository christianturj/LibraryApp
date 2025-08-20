// book management screen
//data synchronization, directly interacts w/ DataManager to modify books
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OwnerBooksState implements AppState {
    private JPanel panel;
    private MainFrame context;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField;
    private JTextField priceField;

    public OwnerBooksState(MainFrame context) {
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
        
        // top panel
        tableModel = new DefaultTableModel(new Object[]{"Book Name", "Price"}, 0);
        table = new JTable(tableModel);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        //middle panel with add controls
        JPanel middlePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        middlePanel.add(new JLabel("Book Name:"));
        nameField = new JTextField();
        middlePanel.add(nameField);
        middlePanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        middlePanel.add(priceField);
        
        // bottom panel w/ buttons
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton addButton = new JButton("Add Book");
        JButton deleteButton = new JButton("Delete Selected");
        JButton backButton = new JButton("Back to Main");
        
        addButton.addActionListener(this::addBook);
        deleteButton.addActionListener(this::deleteBook);
        backButton.addActionListener(e -> context.changeState(new OwnerStartState(context)));
        
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);
        
        panel.add(middlePanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }
    //this is what keeps it in sync with DataManager
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Book book : DataManager.books) {
            tableModel.addRow(new Object[]{book.getName(), book.getPrice()});
        }
    }

    private void addBook(ActionEvent e) {
        //validate input
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            DataManager.books.add(new Book(name, price));
            refreshTable();
            nameField.setText("");
            priceField.setText("");
            //check price formatting
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Invalid price format");
        }
    }

    private void deleteBook(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            DataManager.books.remove(row);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(panel, "Please select a book to delete");
        }
    }
}