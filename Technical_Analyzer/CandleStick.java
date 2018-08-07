/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.bozo;


public class CandleStick {

    double high, low, open, close, volume, realBody, upperShadow,lowerShadow;

    String colour,date;

    CandleStick(String highp, String lowp, String openp, String closep, String volumep,String datep) {
        
        this.high = Double.parseDouble(highp);
        this.close = Double.parseDouble(closep);
        this.low = Double.parseDouble(lowp);
        this.open = Double.parseDouble(openp);
        this.volume = Double.parseDouble(volumep);
        this.date=datep;
        if (open <=close) {
            this.colour = "blue";
            realBody = close - open;
            upperShadow=high-close;
            lowerShadow=open-low;
        }
        if (close < open) {
            this.colour = "red";
            realBody=open-close;
            upperShadow=high-open;
            lowerShadow=close-low;
        }

    }

}
