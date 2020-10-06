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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400,200));
        pack();
        setVisible(true);
    }

    public void addMessage(String message) {
        listModel.addElement(message);
    }
}
