package ruby.core.debugging;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * This class provides a Java Swing Frame to better visualize what is happening
 * in the discord application (messages being sent etc.)
 */
public class BotListenerUI extends JFrame {

    private DefaultTableModel tableModel;

    public BotListenerUI() {
        super("Discord Listener");
        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        //table setup
        String[] infoHeaders = new String[] {"Guild","Channel Name","Channel Type","Author Name","Author ID","Chat Messages","Bot Events"};
        tableModel = new DefaultTableModel(null,infoHeaders);
        JTable table = new JTable(tableModel);

        //table customization
        table.getTableHeader().setFont(new Font("Segoe UI",Font.ITALIC,14));
        table.getTableHeader().setForeground(Color.RED);

        table.setFont(new Font("Segoe UI",Font.PLAIN,12));
        TableColumnModel tcm = table.getColumnModel();
        for (int i = 0; i < table.getColumnCount(); i++) {
            tcm.getColumn(i).setPreferredWidth(75);
        }
        table.getColumn("Chat Messages").setPreferredWidth(300);
        table.getColumn("Bot Events").setPreferredWidth(150);

        //table scroller
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //add content and show
        content.add(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200,350));
        pack();
        setVisible(true);
    }

    /**
     * This method adds a message to each of the three lists on the GUI.
     *
     * @param messageComponents The components of the message to display within the GUI
     */
    public void addMessage(String[] messageComponents) {
        tableModel.addRow(messageComponents);
    }
}
