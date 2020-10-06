import javax.swing.*;
import java.awt.*;

public class BotListenerUI extends JFrame {

    private DefaultListModel listModel;

    public BotListenerUI() {
        super("Listener");
        Container content = getContentPane();

        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        content.add(scrollPane);
    }

    public void addMessage(String message) {
        listModel.addElement(message);
    }
}
