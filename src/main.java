//run from here
//using swing so GUI creation happens on run

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{ MainFrame frame = new MainFrame(); frame.setVisible(true);});
    }
}