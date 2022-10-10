package com.bis.controller;

import java.util.Random;

public class Quotes {
    String quote ;
    public String returnQuotes(){
    Random ran = new Random();
        int num = ran.nextInt(7);
        switch (num) {
            case 0:
                quote  = "";
                break;
            case 1:
                quote  = "";
                break;
            case 2:
                quote  = "";
                break;
            case 3:
                quote  = "";
                break;
            case 4:
                quote  = "";
                break;
            case 5:
                quote  = "";
                break;

            case 6:
                quote  = "";
    }
    return quote;
    }
}
