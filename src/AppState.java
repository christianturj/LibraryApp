//State interface
//interface enforces clear outlines for each state

import javax.swing.JPanel;
public interface AppState {
    JPanel getPanel();
    void initialize();
}