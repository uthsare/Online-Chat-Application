package com.uthsara.websocket_demo.client;


import javax.swing.*;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String username = JOptionPane.showInputDialog(null,
                        "Enter your username: (MAX 16 characters)",
                        "Chat App",
                        JOptionPane.QUESTION_MESSAGE);

                if (username == null || username.isEmpty() || username.length() > 16) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid username!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ClientGUI clientGUI = null;
                try {
                    clientGUI = new ClientGUI(username);
                } catch (Exception e) {
                    throw new RuntimeException(e);}
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                clientGUI.setVisible(true);
            }
        });
    }
}

