import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ActionButton extends JButton implements MouseListener {
    private final ButtonType type;

    public ActionButton(String name, ButtonType type){
        super(name);
        this.addMouseListener(this);
        this.type = type;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (type == ButtonType.FILE_SELECTION){
            JFileChooser fc =new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Main.selectedFile = fc.getSelectedFile();
                this.setText(Main.selectedFile.getName());
                Main.gui.update(Main.gui.getGraphics());
            }
        }else if (type == ButtonType.CONFIRM){
            Main.convertPic();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
