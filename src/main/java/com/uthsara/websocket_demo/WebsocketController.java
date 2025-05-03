package com.uthsara.websocket_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller //marks the class as spring MVC controller
public class WebsocketController {  //handles the web Rquests
    
    private final SimpMessagingTemplate messagingTemplate ;//This is gonna be used by the Websocket to send message to  the clients
    private final WebSocketSessionManager sessionManager; //used to manage users who connected to the server
    
    @Autowired  //This annotaion is part of spring's Dependancy injection Mechanism  . It tells spring to automaticaly inject the required dependancy when creating instance of the websocket controller 
    public WebsocketController(SimpMessagingTemplate messagingTemplate, WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;

    }

    @MessageMapping("/message") //binds messages to /app/message route
    public void handleMessage(Message message){
        
        System.out.println("Recieved Messsage From User:"+message.getUser()+": "+ message.getMessage());
        messagingTemplate.convertAndSend("/topic/message",message);
        System.out.println("Sent messages to /topic/message: "+message.getUser()+": "+message.getMessage()); //  for debuging purposes]
    }

    @MessageMapping("/connect") //binds messages to /app/connect route
    public void connectUser(String username){
        sessionManager.addUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username+" connected to the server");//for debugging
    }

    @MessageMapping("/disconnect") //binds messages to /app/disconnect route
    public void disconnectUser(String username){
        sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println(username+" disconnected");//for debugging 
    }


}
