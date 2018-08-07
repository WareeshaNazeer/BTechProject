/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.bozo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  @author Wareesha_Nazeer
 *
 */
public class TechnicalAnalysis {

    private int EngulfingBullish(CandleStick day1, CandleStick day2) {

        if (day1.colour.equals("red") && day2.colour.equals("blue")) {
            if (day1.realBody < day2.realBody) {
                return (int) (day2.realBody - day1.realBody);
            }
            return 0;

        } else {
            return 0;
        }
    }

    private int EngulfingBearish(CandleStick day1, CandleStick day2) {

        if (day1.colour.equals("blue") && day2.colour.equals("red")) {
            if (day1.realBody < day2.realBody) {
                return (int) (day2.realBody - day1.realBody);
            }
            return 0;

        } else {
            return 0;
        }
    }

    private boolean engulfingBullishDetector(CandleStick day1, CandleStick day2) throws IOException {

        if ((EngulfingBullish(day1, day2) > 0)) {
            return true;

        } else {
            return false;
        }

    }

    private boolean engulfingBearishDetector(CandleStick day1, CandleStick day2) {

        if ((EngulfingBearish(day1, day2) > 0)) {
            return true;

        } else {
            return false;
        }

    }

    private int trend(CandleStick day1, CandleStick day2) {
        return (int) (day1.high - day2.high);

    }

    public static CandleStick stringToCandleStick(String s) {
        String attrDay[] = s.split(",");
        CandleStick day = new CandleStick(attrDay[2], attrDay[3], attrDay[1], attrDay[4], attrDay[5], attrDay[0]);
        return day;
    }

    private boolean bullishMarubozoDetector(CandleStick day) {
        int flexibilityPercent = 10;
        if (((((day.open - day.low) / day.realBody) * 100) < flexibilityPercent) && ((((day.high - day.close) / day.realBody) * 100) < flexibilityPercent)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean bearishMarubozoDetector(CandleStick day) {
        int flexibilityPercent = 10;
        if (((((day.high - day.open) / day.realBody) * 100) < flexibilityPercent) && ((((day.close - day.low) / day.realBody) * 100) < flexibilityPercent)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean spinningTopDetector(CandleStick day2) {

        if ((day2.realBody > day2.upperShadow) && (day2.realBody > day2.lowerShadow) && ((day2.realBody / day2.upperShadow) * 100 > 30) && ((day2.realBody / day2.lowerShadow) * 100 > 30)) {
            return true;
        } else {
            return false;
        }

    }

    private boolean dojiDetector(CandleStick day) {

        if (day.realBody / (day.high - day.low) * 100 < 50) {
            return true;
        } else {
            return false;
        }

    }

    private boolean paperUmbrellaDetector(CandleStick day) {
        if ((day.lowerShadow / day.realBody) > 2 && (day.upperShadow / (day.high - day.low) * 100) < 5) {
            return true;
        } else {
            return false;
        }
    }

    private boolean shootingStarDetector(CandleStick day) {
        if ((day.upperShadow / day.realBody) > 2) {
            return true;
        } else {
            return false;
        }

    }

    private boolean bullishHaramiDetector(CandleStick day1, CandleStick day2) {
        if (day1.colour.equals("red") && day2.colour.equals("blue") && (day1.close < day2.open)) {
            return true;

        } else {
            return false;
        }
    }

    private boolean bearishHaramiDetector(CandleStick day1, CandleStick day2) {
        if (day1.colour.equals("blue") && day2.colour.equals("red") && (day1.close > day2.open)) {
            return true;

        } else {
            return false;
        }
    }

    private boolean morningStarDetector(CandleStick day1, CandleStick day2) {
        if (uncertainity(day1) && gapUpOpeningDetector(day1, day2) && (day2.colour.equals("blue"))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean eveningStarDetector(CandleStick day1, CandleStick day2) {
        if (uncertainity(day1) && gapDownOpeningDetector(day1, day2) && (day2.colour.equals("red"))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean uncertainity(CandleStick day) {
        return dojiDetector(day) || spinningTopDetector(day);
    }

    private boolean gapUpOpeningDetector(CandleStick day1, CandleStick day2) {
        return (day1.close < day2.open);
    }

    private boolean gapDownOpeningDetector(CandleStick day1, CandleStick day2) {
        return (day1.close > day2.open);
    }

    private void uptrendDetector(CandleStick day1, CandleStick day2, BufferedWriter bw) throws IOException {
        if (eveningStarDetector(day1, day2)) {
            //System.out.println("Evening Star Pattern found at " + day2.date);
            bw.write(10 + ",");
        } else {
            bw.write(0 + ",");
        }
        if (engulfingBearishDetector(day1, day2)) {
            //System.out.println("Engulfing Bearish Pattern found at " + day2.date);
            bw.write(8 + ",");
        } else {
            bw.write(0 + ",");
        }
//        if (spinningTopDetector(day2)) {
//            //System.out.println("Uptrend Spinning Top found at " + day2.date);
//            bw.write(2 + ",");
//        } else {
//            bw.write(0 + ",");
//        }

        if (bearishHaramiDetector(day1, day2)) {
            //System.out.println(" Bearish Harami Pattern found at " + day2.date);
            bw.write(2 + ",");
        } else {
            bw.write(0 + ",");
        }
//        if (dojiDetector(day2)) {
//            //System.out.println("Doji Detected in uptrend at " + day2.date);
//            bw.write(1 + ",");
//        } else {
//            bw.write(0 + ",");
//        }
        if (paperUmbrellaDetector(day2)) {
            //System.out.println("Hanging Man detected at " + day2.date);
            bw.write(3 + ",");
        } else {
            bw.write(0 + ",");
        }
        if (shootingStarDetector(day2)) {
            //System.out.println(day2.colour + " Shooting Star Detected at " + day2.date);
            bw.write(6 + ",");
        } else {
            bw.write(0 + ",");
        }

    }

    private void downtrendDetector(CandleStick day1, CandleStick day2, BufferedWriter bw) throws IOException {
        if (engulfingBullishDetector(day1, day2)) {
            //System.out.println("Engulfing Bullish Pattern found at " + day2.date);
            bw.write(8 + ",");

        } else {
            bw.write(0 + ",");
        }
        if (bullishHaramiDetector(day1, day2)) {
            //System.out.println(" Bullish Harami Pattern found at " + day2.date);
            bw.write(2 + ",");

        } else {
            bw.write(0 + ",");
        }
//        if (spinningTopDetector(day2)) {
//            //System.out.println("Downtrend Spinning Top found at " + day2.date);
//            bw.write(1 + ",");
//        } else {
//            bw.write(0 + ",");
//        }
//        if (dojiDetector(day2)) {
//            //System.out.println("Doji Detected in downtrend at " + day2.date);
//            bw.write(1 + ",");
//        } else {
//            bw.write(0 + ",");
//        }
        if (paperUmbrellaDetector(day2)) {
            //System.out.println("Hammer detected at " + day2.date);
            bw.write(3 + ",");
        } else {
            bw.write(0 + ",");
        }
        if (morningStarDetector(day1, day2)) {
            //System.out.println("Morning Star Pattern found at " + day2.date);
            bw.write(10 + ",");
        } else {
            bw.write(0 + ",");
        }
    }

    //function to find EMA
    private double EMA(double previousEMA, int timePeriod, double close) {
        double multiplier;
        multiplier = 2 / (double) (timePeriod + 1);

        return (((close - previousEMA) * multiplier) + previousEMA);
    }

    //function to find MACD
    private double MACD(double slowEMA, double fastEMA) {
        return slowEMA - fastEMA;
    }

    private double SMA(double previousSMA, int timePeriod, double dropClose, double addClose) {
        return (previousSMA - dropClose + addClose / timePeriod);
    }

    private double average(double days[]) {
        double sum = 0;
        for (int i = 0; i < days.length; i++) {
            sum = days[i] + sum;
        }
        return sum / days.length;
    }

    private double averageForRSI(double average, int timePeriod, double currentTick) {
        return ((average * (timePeriod - 1)) + currentTick) / timePeriod;

    }

    private double RSI(double averageLoss, double averageGain) {
        return (100 - (100 / (1 + averageLoss / averageGain)));
    }

    private double OBV(CandleStick day1, CandleStick day2, double previousOBV) {
        if (day2.close > day1.close) {
            return previousOBV + day2.volume;
        } else if (day2.close < day1.close) {
            return previousOBV - day2.volume;
        } else {
            return previousOBV;
        }
    }

//Main function that calls all other detector with the provided parameters
    void patternDetector(File fileName, File writename) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new ReverseLineInputStream(fileName)));
        FileWriter fw = new FileWriter(writename);
        BufferedWriter bw = new BufferedWriter(fw);
        RunningStat rs = new RunningStat();
        String prev, line, line2;
        line = in.readLine();

        int trendForDays = 0, trend = 0, dayCounter = 0, slowEMATimePeriod = 26, fastEMATimePeriod = 12, signalLineTimePeriod = 9, averageTimePeriod = 14, MACDInd = 0, lBB = 0, uBB = 0, noOfDaysReturn = 5;
        double slowEMA = 0, fastEMA = 0, slowSMA = 0, fastSMA = 0, MACD = 0, signalLineEMA = 0, currentGain = 0, currentLoss = 0, averageGain = 0, averageLoss = 0, RSI, currentOBV = 0, t, prevSignalLine = 0, avgReturn = 0, prevMACD = 0, centerLine = 0, lowerBand = 0, upperBand = 0;

        double[] fastDayClose = new double[fastEMATimePeriod];
        double[] slowDayClose = new double[slowEMATimePeriod];
        double[] signalLine = new double[signalLineTimePeriod];

        while (true) {
            prev = line;
            line = in.readLine();
            in.mark(1000);

            line2 = in.readLine();
            in.reset();
            if (line2 == null) {
                break;
            }
            CandleStick day1 = stringToCandleStick(prev);

            CandleStick day2 = stringToCandleStick(line);
            CandleStick day3 = stringToCandleStick(line2);
            double change = (double)(((day3.close - day2.close) / day2.close) * 100);
            /*if (change > 0) {
                change = 0;
            } else if (change < 0) {
                change = -1;
            }*/

            if (dayCounter < fastEMATimePeriod) {
                fastDayClose[dayCounter] = day2.close;
            }

            if (dayCounter < slowEMATimePeriod) {
                slowDayClose[dayCounter] = day2.close;
            }
            bw.write(day2.date + ",");
            //Downtrend Detection Condition
            if (trend(day1, day2) > 0 && trend >= 0) {
                trend = trend + trend(day1, day2);
                trendForDays = trendForDays + 1;
                bw.write("0,0,0,0,0,");
                downtrendDetector(day1, day2, bw);

            } else if (trend(day1, day2) > 0 && trend < 0) {
                trend = trend(day1, day2);
                trendForDays = 1;
                uptrendDetector(day1, day2, bw);
                bw.write("0,0,0,0,");
            } //Uptrend Detection Condition
            else if (trend(day1, day2) < 0 && trend <= 0) {
                trend = trend + trend(day1, day2);
                trendForDays++;
                uptrendDetector(day1, day2, bw);
                bw.write("0,0,0,0,");
            } else {
                trend = trend(day1, day2);
                trendForDays = 1;
                bw.write("0,0,0,0,0,");
                downtrendDetector(day1, day2, bw);

            }

//            if (bullishMarubozoDetector(day2)) {
//                //System.out.println("Bullish Marubozo Pattern found at " + day2.date);
//                bw.write(1 + ",");
//            } else {
//                bw.write(0 + ",");
//            }
//
//            if (bearishMarubozoDetector(day2)) {
//                //System.out.println("Bearish Marubozo Pattern found at " + day2.date);
//                bw.write(1 + ",");
//            } else {
//                bw.write(0 + ",");
//            }
            if (dayCounter == slowEMATimePeriod) {
                slowSMA = average(slowDayClose);
                slowEMA = slowSMA;
            }

            if (dayCounter == fastEMATimePeriod) {
                fastSMA = average(fastDayClose);
                fastEMA = fastSMA;
            }
            if (dayCounter > slowEMATimePeriod) {
                t = EMA(slowEMA, slowEMATimePeriod, day2.close);
                slowEMA = t;
            }
            if (dayCounter > fastEMATimePeriod) {
                //System.out.println(EMA(fastEMA, fastEMATimePeriod, 0));
                //fastEMA=t;
            }

            if (dayCounter > slowEMATimePeriod) {
                //System.out.println(fastEMA+"\t"+slowEMA);
                prevMACD = MACD;
                MACD = fastEMA - slowEMA;
                if (prevMACD < 0 && MACD > 0) {
                    MACDInd = -1;
                } else if (prevMACD > 0 && MACD < 0) {
                    MACDInd = 1;
                } else {
                    MACDInd = 0;
                }
            }
//            if (dayCounter > slowEMATimePeriod && dayCounter < slowEMATimePeriod + 10) {
//                signalLine[dayCounter - slowEMATimePeriod - 1] = day2.close;
//                signalLineEMA = average(signalLine);
//            }
            if (dayCounter > slowEMATimePeriod && dayCounter == slowEMATimePeriod + 10) {
                signalLineEMA = MACD;
            }
            if (dayCounter > slowEMATimePeriod + 10) {
                prevSignalLine = signalLineEMA;

                signalLineEMA = averageForRSI(signalLineEMA, signalLineTimePeriod, MACD);
                //System.out.println(signalLineEMA+"\t"+MACD);
//                if (signalLineEMA > MACD) {
//                    MACDInd = 1;
//                } else if (signalLineEMA < MACD) {
//                    MACDInd = -1;
//                } else {
//                    MACD = 0;
//                }

            }
            if ((day2.close - day1.close) > 0) {
                currentGain = day2.close - day1.close;
                currentLoss = 0;
            }
            if ((day2.close - day1.close) < 0) {
                currentLoss = Math.abs(day2.close - day1.close);
                currentGain = 0;
            }

            rs.put(day2.close);
            centerLine = rs.getAverage();
            upperBand = centerLine + 2 * rs.getStandardDeviation();
            lowerBand = centerLine - 2 * rs.getStandardDeviation();
            //System.out.println(rs.getStandardDeviation() + "\t" + rs.getAverage()+"\t"+day2.close);
            if (lowerBand > day2.close && dayCounter > 20) {
                lBB = 1;

            } else {
                lBB = 0;
            }
            if (upperBand < day2.close && dayCounter > 20) {
                uBB = 1;
            } else {
                uBB = 0;
            }

            averageGain = averageForRSI(averageGain, averageTimePeriod, currentGain);
            averageLoss = averageForRSI(averageLoss, averageTimePeriod, currentLoss);
            RSI = RSI(averageLoss, averageGain);
            if (RSI >= 70) {
                RSI = 70 - RSI;
            } else if (RSI <= 30) {
                RSI = RSI;
            } else {
                RSI = 0;
            }
            avgReturn = (((noOfDaysReturn - 1) * avgReturn) + (day2.close - day1.close) / day1.close) / noOfDaysReturn;
            currentOBV = OBV(day1, day2, currentOBV);
            bw.write((int) MACDInd + "," + (int) currentOBV + "," + (int) RSI + "," + lBB + "," + uBB + "," + change +","+avgReturn +"\n");
            dayCounter++;
        }
    }
}

class RunningStat {

    private int count = 0;
    private double average = 0.0;
g    private double pwrSumAvg = 0.0;
    private double stdDev = 0.0;

    /**
     * Incoming new values used to calculate the running statistics
     *
     * @param value
     */
    public void put(double value) {

//    count++;
        if (count < 20) {
            count++;
        }

        //System.out.print(average + " ");
        average += (value - average) / count;
        //System.out.println(average + " ");
        pwrSumAvg += (value * value - pwrSumAvg) / count;
        stdDev = Math.sqrt((pwrSumAvg * count - count * average * average) / (count - 1));

    }

    public double getAverage() {

        return average;
    }

    public double getStandardDeviation() {

        return Double.isNaN(stdDev) ? 0.0 : stdDev;
    }

}
