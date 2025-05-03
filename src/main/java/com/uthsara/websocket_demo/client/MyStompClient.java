package com.uthsara.websocket_demo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.uthsara.websocket_demo.Message;

public class MyStompClient {
    
    private StompSession session; //allows to connect to the servers
    private String username;        //username of the client

    
    public MyStompClient(MessageListner messageListner,String username){
        this.username=username;

        List<Transport> transports=new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);  // Basically allows us to use stomp client
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());     //converts java objects in to JSON 

        //init new session handler
        StompSessionHandler sessionHandler = new MyStompSessionHandler(messageListner,username);
        String url ="ws://localhost:8080/ws";   //use ws://for WebSocket
        
        try {
            session = stompClient.connectAsync(url, sessionHandler).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    
    
    public void sendMessage(Message message){
        try {
            //Using stomp session to send the message
            session.send("/app/message",message);
            System.out.println("Message sent: "+message.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(String username){
        
        session.send("/app/disconnect",username);
        //session.disconnect();
        System.out.println(username+" disconnected");
    }

}