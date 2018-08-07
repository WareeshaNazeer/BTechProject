/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.bozo;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Wareesha_Nazeer
 */
public class AppTest {

    public static void main(String args[]) throws IOException {
        int i = 0;
        String companyName = new String();
        TechnicalAnalysis tA = new TechnicalAnalysis();
//        System.out.println("Enter the company name");
//        Scanner in = new Scanner(System.in);
//        String companyName= in.next();
        String companies[] = {"itc", "adaniport", "asianpaint", "axisbank", "bhartiairtel", "cipla", "gail", "hdfc", "icicibank", "itc", "lt", "ongc", "sunpharma", "tatamotors"};
        while (i < companies.length) {
            companyName = companies[i];
            i++;
            File file = new File("C:\\Users\\Wareesha_Nazeer\\Bitnami Trac Stack projects\\Automated Attendance System\\Bozo\\src\\main\\java\\com\\example\\bozo\\quotes\\" + companyName + ".csv");
            File writeToFile = new File("C:\\Users\\Wareesha_Nazeer\\Bitnami Trac Stack projects\\Automated Attendance System\\Bozo\\src\\main\\java\\com\\example\\bozo\\quotes\\pred\\update\\" + companyName + ".pred1");
            tA.patternDetector(file, writeToFile);
            //new CandlestickChartGenerator("C:\\Users\\Wareesha_Nazeer\\Bitnami Trac Stack projects\\Automated Attendance System\\Bozo\\src\\main\\java\\com\\example\\bozo\\quotes\\"+companyName+".csv").setVisible(true);
        }
    }
}
