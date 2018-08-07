package com.example.bozo;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.*;
import java.util.*;
import java.util.List;

public class CandlestickChartGenerator extends JFrame {

    public CandlestickChartGenerator(String stockSymbol) {
        super("CandlestickChartGenerator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DateAxis domainAxis = new DateAxis("Date");
        NumberAxis rangeAxis = new NumberAxis("Price");
        CandlestickRenderer renderer = new CandlestickRenderer();
        XYDataset dataset = getDataSet(stockSymbol);

        XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

        //Do some setting up, see the API Doc
        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setDrawVolume(false);
        rangeAxis.setAutoRangeIncludesZero(false);
        domainAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
        
        
        


        //Now create the chart and chart panel
        JFreeChart chart = new JFreeChart(stockSymbol, null, mainPlot, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        //Enable Zooming
        chartPanel.setMouseZoomable(true);
        chartPanel.setMouseWheelEnabled(true);
        

        this.add(chartPanel);
        this.pack();
    }

    protected AbstractXYDataset getDataSet(String stockSymbol) {
        //This is the dataset we are going to create
        DefaultOHLCDataset result = null;
        //This is the data needed for the dataset
        OHLCDataItem[] data;

        //This is where we go get the data, replace with your own data source
        data = getData(stockSymbol);

        //Create a dataset, an Open, High, Low, Close dataset
        result = new DefaultOHLCDataset(stockSymbol, data);

        return result;
    }

    //This method uses yahoo finance to get the OHLC data
    protected OHLCDataItem[] getData(String stockSymbol) {
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        File file= new File(stockSymbol);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new ReverseLineInputStream(file)));
            String line;

            line = in.readLine();
            while (line != null) {
                CandleStick cS = TechnicalAnalysis.stringToCandleStick(line);
                DateFormat df = new SimpleDateFormat("y-M-d");

               

                Date date = df.parse(cS.date);
                double open = cS.open;
                double high = cS.high;
                double low = cS.low;
                double close = cS.close;
                double volume = cS.volume;
                

                OHLCDataItem item = new OHLCDataItem(date, open, high, low, close, volume);
                dataItems.add(item);
                line = in.readLine();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Data from Yahoo is from newest to oldest. Reverse so it is oldest to newest
        Collections.reverse(dataItems);

        //Convert the list into an array
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);

        return data;
    }

}
