package com.uthsara.websocket_demo.client;

import java.util.ArrayList;

import com.uthsara.websocket_demo.Message;

public interface MessageListner {
    
    void onMessageRecieve(Message message);
    void onActiveUsersUpdated(ArrayList<String> users);
}