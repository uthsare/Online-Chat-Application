package com.uthsara.websocket_demo.client;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.uthsara.websocket_demo.Message;

public class MyStompSessionHandler  extends StompSessionHandlerAdapter {

    private String username;
    private MessageListner messageListner;
    
    public MyStompSessionHandler(MessageListner messageListner,String username){
        this.username = username;
        this.messageListner=messageListner;
    } 

    //Use once the user connected successfully
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        // subcribes to topic/message route and instantiate new StompFrame Handler for handle the incoming messages
        System.out.println("Client connected to the server");

        session.send("/app/connect", username);//send the username to the server
        
        System.out.println("Client sent username: "+username);//debugging purpose
        
        session.subscribe("/topic/message", new StompFrameHandler() {
            @Override
            //informs client about the payload(incoming messages)
            public Type getPayloadType(StompHeaders headers) {
               System.out.println("Payload type: "+Message.class); 
                return Message.class;
            }

            
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Client received message: "+payload);
                    if (payload instanceof Message) {
                        Message message=(Message)payload;
                        messageListner.onMessageRecieve(message);
                        System.out.println("Received message: " + message.getUser() + ": " + message.getMessage());
                    } else {
                        System.out.println("Received unexpected payload type: " + payload.getClass());
                    }
            }
        });

        System.out.println("Client Subscribed to /topic/message");

        session.subscribe("/topic/users",new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ArrayList<String>().getClass();
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    if (payload instanceof ArrayList) {
                        ArrayList<String> activeUsers=(ArrayList<String>)payload;
                        messageListner.onActiveUsersUpdated(activeUsers);
                        System.out.println("Recieved active users");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            
        });
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        // TODO Auto-generated method stub
        super.handleTransportError(session, exception);
    }

    
}