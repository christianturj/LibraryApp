//customer screen
//does purchase handling
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CustomerStartState implements AppState {
    private JPanel panel;
    private MainFrame context;
    private Customer customer;
    private JTable booksTable;
    private DefaultTableModel tableModel;

    public CustomerStartState(MainFrame context, Customer customer) {
        this.context = context;
        this.customer = customer;
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

        //top panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel(String.format("welcome: %s. you have: %d points. so your status is: %s",
                customer.getUsername(),
                customer.getPoints(),
                customer.getStatus())));
        panel.add(topPanel, BorderLayout.NORTH);

        // middle
        tableModel = new DefaultTableModel(new Object[]{"Book Name", "Price", "Select"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 2 ? Boolean.class : Object.class;
            }
        };
        booksTable = new JTable(tableModel);
        refreshBooksTable();
        panel.add(new JScrollPane(booksTable), BorderLayout.CENTER);

        // bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton backButton = new JButton("Back");
        JButton buyButton = new JButton("Buy");
        JButton redeemButton = new JButton("Redeem & Buy");
        JButton logoutButton = new JButton("Logout");

        backButton.addActionListener(e -> context.changeState(new LoginState(context)));
        buyButton.addActionListener(e -> handlePurchase(false));
        redeemButton.addActionListener(e -> handlePurchase(true));
        logoutButton.addActionListener(e -> context.changeState(new LoginState(context)));

        bottomPanel.add(backButton);
        bottomPanel.add(buyButton);
        bottomPanel.add(redeemButton);
        bottomPanel.add(logoutButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshBooksTable() {
        tableModel.setRowCount(0);
        for (Book book : DataManager.books) {
            tableModel.addRow(new Object[]{book.getName(), book.getPrice(), false});
        }
    }

    private List<Book> getSelectedBooks() {
        List<Book> selectedBooks = new ArrayList<>();
        for (int i = 0; i < booksTable.getRowCount(); i++) {
            Boolean selected = (Boolean) booksTable.getValueAt(i, 2);
            if (selected != null && selected) {
                String bookName = (String) booksTable.getValueAt(i, 0);
                double price = (Double) booksTable.getValueAt(i, 1);
                selectedBooks.add(new Book(bookName, price));
            }
        }
        return selectedBooks;
    }

    private void handlePurchase(boolean redeem) {
        List<Book> selectedBooks = getSelectedBooks();
        
        if (selectedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "pls select at least one book");
            return;
        }

        double total = selectedBooks.stream()
                .mapToDouble(Book::getPrice)
                .sum();

        if (redeem) {
            int redeemAmount = customer.getPoints() / 100;
            total = Math.max(0, total - redeemAmount);
            customer.setPoints(customer.getPoints() - (redeemAmount * 100));
        }

        // update points
        customer.setPoints(customer.getPoints() + (int)(total * 10));
        
        //remove purchased books
        selectedBooks.forEach(book -> DataManager.books.removeIf(
            b -> b.getName().equals(book.getName()) && 
                 b.getPrice() == book.getPrice()
        ));
        
        // sync
        refreshBooksTable();
        context.changeState(new CustomerCostState(context, customer, total));
    }
}