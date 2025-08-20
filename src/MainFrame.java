//main window management for State pattern

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private AppState currentState;
    
    public MainFrame() {
        setTitle("Book Store App");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //handles window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataManager.saveData();
                dispose();
            }
        });
        //load initial data and show login
        DataManager.loadData();
        changeState(new LoginState(this));
    }
    //state transition mechanism
    public void changeState(AppState newState) {
        if (currentState != null) {
            remove(currentState.getPanel());
        }
        currentState = newState;
        add(currentState.getPanel());
        revalidate();
        repaint();
    }
}