package com.uthsara.websocket_demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service //indicates that an annotated class is a compnent of the spring service
public class WebSocketSessionManager { //This class is used to manage users who connected to the server
    
    private final ArrayList<String> activeusernames = new ArrayList<>(); //list of active users
    private final SimpMessagingTemplate messagingTemplate; //used to send messages to the client
    

    @Autowired
    /**
     * This is the constructor of the class
     * @param messagingTemplate
     */
    public WebSocketSessionManager(SimpMessagingTemplate messagingTemplate) {
        
        this.messagingTemplate = messagingTemplate; //follows singelton pattern
    }

    /**
     * This method is used to add the user to the list of active users
     * @param username
     */
    public void addUsername(String username) {
        
        activeusernames.add(username); //add the user to the list
    }

    /**
     * This method is used to remove the user from the list of active users
     * @param username
     */
    public void removeUsername(String username) {
        
        activeusernames.remove(username); //remove the user from the list
    }

    /**
     * This method is used to broadcast the list of active users to the client
     */
    public void broadcastActiveUsernames() {
        
        messagingTemplate.convertAndSend("/topic/activeUsers", activeusernames); //send the list of active users to the client
        System.out.println("Broadcasting active users to /topic/users"+activeusernames); //for debugging
    }
}