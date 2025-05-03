package com.uthsara.websocket_demo.client;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

public class Utilities {
    
    public static final Color TRANSPARENT__COLOR = new Color(0, 0, 0, 0);
    public static final Color PRIMARY__COLOR = Color.decode("#2F2020");
    public static final Color SECONDARY__COLOR = Color.decode("#484444");
    public static final Color TEXT__COLOR = Color.white;

    public static EmptyBorder addPadding(int top, int left, int bottom,int right){
        return new EmptyBorder(top,left,bottom,right);
    }
    

    
}