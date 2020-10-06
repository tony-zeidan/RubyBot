import javax.swing.*;
import java.awt.*;

public class BotListenerUI extends JFrame {

    private DefaultListModel listModel;

    public BotListenerUI() {
        super("Listener");
        Container content = getContentPane();

        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        content.add(list);
    }

    public void addMessage(String message) {
        listModel.addElement(message);
    }

}
