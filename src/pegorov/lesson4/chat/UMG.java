package pegorov.lesson4.chat;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import java.awt.*;
import java.util.Enumeration;

public class UMG extends JFrame {
    private int width = 500;
    private int sizeX = 150;
    private int sizeY = 250;

    public void initField() {
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(150,150, width, sizeY);

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        JPanel desk = new JPanel();

        JTextArea dialogField = new JTextArea();
        dialogField.setEditable(false);
        dialogField.setLineWrap(true);
        dialogField.setWrapStyleWord(true);
        dialogField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JScrollPane jsp = new JScrollPane(dialogField,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       
        JTextField textField = new JTextField();
        textField.addActionListener(action -> {
            sendMessage(textField, dialogField);
        });

        JButton sendMessage = new JButton("Send");
        sendMessage.addActionListener(action -> {
            sendMessage(textField, dialogField);
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(textField);
        controlPanel.add(sendMessage);
        controlPanel.setSize(width - sizeX, sizeY);

        desk.setLayout(new BoxLayout(desk, BoxLayout.X_AXIS));
        desk.add(jsp);
        desk.setSize(sizeX, sizeY);

        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        getContentPane().add(desk, BorderLayout.CENTER);

        setVisible(true);
    }

    private void sendMessage(JTextField textField, JTextArea dialogField) {
        if (textField.getText().equals("")) {
            return;
        }

        dialogField.append("\n" + textField.getText());
        textField.setText("");
    }
}
