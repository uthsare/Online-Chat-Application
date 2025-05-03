package com.uthsara.websocket_demo.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.uthsara.websocket_demo.Message;

public class ClientGUI extends JFrame implements MessageListner  {

    private JPanel connectedUsersPanel, messagePanel;
    private MyStompClient myStompClient;
    private String username;
    private JScrollPane messagePanelScrollPane;

    public ClientGUI(String username){

        super("User: "+username);//Call the constructor of the parent class which is JFrame

        this.username=username;//set the username of the client
        myStompClient=new MyStompClient(this,username);//create a new instance of MyStompClient with the username and the current instance of the class

        //configure the frame
        setSize(1218, 685);
        setLocationRelativeTo(null);//center the frame when it is launched.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//do nothing when the close button is clicked
        addWindowListener(new WindowAdapter(){//add a window listener to the frame
           
            @Override
            public void windowClosing(WindowEvent e){//override the windowClosing method
                int option = JOptionPane.showConfirmDialog(ClientGUI.this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);//show a confirmation dialog
                if(option == JOptionPane.YES_OPTION){//if the user clicks yes
                    myStompClient.disconnect(username);
                    ClientGUI.this.dispose();//dispose the frame
                }
            }
        }
        );

        getContentPane().setBackground(Utilities.PRIMARY__COLOR);//set the background color of the frame to green
        addGUIComponents();//add the components to the frame
        
    }

    private void addGUIComponents() {//add the components to the frame
        
        addConnectedUsersComponent();
        addChatComponent();
    }

    /**
     * This method is used to add the connected users panel to the frame
     */
    private void addConnectedUsersComponent() {
        
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setBorder(Utilities.addPadding(10,10,10,10));
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel,BoxLayout.Y_AXIS));//set the layout of the panel to BoxLayout with Y_AXIS
        connectedUsersPanel.setBackground(Utilities.SECONDARY__COLOR);//set the background color of the panel to transparent
        connectedUsersPanel.setPreferredSize(new Dimension(200,getHeight()));//get the height of the frame and set it as the preferred height of the panel

        JLabel connectedUsersLabel = new JLabel("Connected Users");//create a new JLabel with the text "Connected Users"
        connectedUsersLabel.setFont(new Font("Inter", Font.BOLD, 11));//set the font of the label
        connectedUsersLabel.setForeground(Utilities.TEXT__COLOR);//set the foreground color of the label to BLACK
        connectedUsersPanel.add(connectedUsersLabel);//add the label to the panel

        add(connectedUsersPanel ,BorderLayout.WEST);//add the panel to the frame at the WEST position

        
    }

    /**
     * This method is used to add the chat panel to the frame
     */
    private void addChatComponent() {

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());//set the layout of the panel to BorderLayout
        chatPanel.setBackground(Utilities.TRANSPARENT__COLOR);//set the background color of the panel to transparent

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel,BoxLayout.Y_AXIS));//set the layout of the panel to BoxLayout with Y_AXIS
        messagePanel.setBackground(Utilities.TRANSPARENT__COLOR);//set the background color of the panel to transparent

        messagePanelScrollPane = new JScrollPane(messagePanel);
        messagePanelScrollPane.setBackground(Utilities.TRANSPARENT__COLOR);
        messagePanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messagePanelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        messagePanelScrollPane.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                revalidate();
                repaint();
            }
        });

        chatPanel.add(messagePanelScrollPane,BorderLayout.CENTER);//add the panel to the center of the chatPanel

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(Utilities.addPadding(10,10,10,10));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Utilities.TRANSPARENT__COLOR);

        JTextField inputField = new JTextField();

        inputField.addKeyListener(new KeyAdapter() {
           
            @Override
            public void keyTyped(KeyEvent e) {
                
                if (e.getKeyChar()==KeyEvent.VK_ENTER) {
                    
                    String input=inputField.getText();
                   
                    if(input.isEmpty()) return;

                    inputField.setText("");



                    myStompClient.sendMessage(new Message(username,input));
                }
            }    
        });

        inputField.setBackground(Utilities.SECONDARY__COLOR);
        inputField.setForeground(Utilities.TEXT__COLOR);
        inputField.setBorder(Utilities.addPadding(0, 10, 0, 10));
        inputField.setFont(new Font("Inter",Font.PLAIN,16));
        inputField.setPreferredSize(new Dimension(inputPanel.getWidth(),50));
        inputPanel.add(inputField,BorderLayout.CENTER);
        chatPanel.add(inputPanel,BorderLayout.SOUTH);



        add(chatPanel,BorderLayout.CENTER);//add the chatPanel to the frame at the CENTER position
    }  
    
    /**
     * This method is used to create a chat message component
     * @param message The message object
     * @return JPanel The JPanel object
     */
    private JPanel createChatMessageComponent(Message message){

        JPanel chatMessage = new JPanel();
        chatMessage.setBackground(Utilities.TRANSPARENT__COLOR);
        chatMessage.setLayout(new BoxLayout(chatMessage, BoxLayout.Y_AXIS));
        chatMessage.setBorder(Utilities.addPadding(20,20,10,20));

        JLabel usernameLabel= new JLabel(message.getUser());
        usernameLabel.setFont(new Font("Inter",Font.BOLD,11));
        usernameLabel.setForeground(Utilities.TEXT__COLOR);
        chatMessage.add(usernameLabel);

        JLabel messageLabel = new JLabel();
        messageLabel.setText("<html>" +
                "<body style='width:" + (0.60 * getWidth()) + "'px>" +
                message.getMessage() +
                "</body>"+
                "</html>");


        messageLabel.setFont(new Font("Inter",Font.PLAIN,14));
        messageLabel.setForeground(Utilities.TEXT__COLOR);
        chatMessage.add(messageLabel);

        return chatMessage;
    }

    /**
     * This method is called when a message is received
     */
    @Override
    public void onMessageRecieve(Message message) {
        
        System.out.println("onMessageReceive");
        messagePanel.add(createChatMessageComponent(message));
        revalidate();
        repaint();

    }
    
    //This method is called when the active users list is updated
    @Override
    public void onActiveUsersUpdated(ArrayList<String> users) {

        if(connectedUsersPanel.getComponents().length>= 2){
            connectedUsersPanel.remove(1);
        }

        JPanel userListPanel = new JPanel();
        userListPanel.setBackground(Utilities.TRANSPARENT__COLOR);
        userListPanel.setLayout(new BoxLayout(userListPanel,BoxLayout.Y_AXIS));

        for(String user: users){
            JLabel userLabel = new JLabel(user);
            userLabel.setFont(new Font("Inter",Font.PLAIN,11));
            userLabel.setForeground(Utilities.TEXT__COLOR);
            userListPanel.add(userLabel);
        }

        connectedUsersPanel.add(userListPanel);
        
        revalidate();
        repaint();

        
    }
}