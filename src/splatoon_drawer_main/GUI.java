package splatoon_drawer_main;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI(){
        this.setTitle("Arduino matrix maker");
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel bLayout = new JPanel();
        bLayout.setLayout(new BoxLayout(bLayout, BoxLayout.LINE_AXIS));

        JPanel buttonOne = new JPanel();
        ActionButton buttonLoadFile = new ActionButton("Select file", ButtonType.FILE_SELECTION);
        buttonOne.add(buttonLoadFile);
        JPanel buttonTwo = new JPanel();
        ActionButton buttonConfirm = new ActionButton("Confirm", ButtonType.CONFIRM);
        buttonTwo.add(buttonConfirm);

        bLayout.add(buttonOne);
        bLayout.add(buttonTwo);
        Container content = this.getContentPane();
        content.add(bLayout);
        this.setVisible(true);
    }
}
