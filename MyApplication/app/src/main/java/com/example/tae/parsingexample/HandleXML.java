package com.example.tae.parsingexample;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sairamkrishna on 4/11/2015.
 */

public class HandleXML {
    private String country = "county";
    private String temperature = "temperature";
    private double cTemperature = 0;
    private String humidity = "humidity";
    private String pressure = "pressure";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXML(String url){
        this.urlString = url;
    }

    public String getCountry(){
        return country;
    }

    public double getTemperature(){
        cTemperature = (Float.parseFloat(temperature) - 273.15);
        return cTemperature;
    }

    public String getHumidity(){
        return humidity;
    }

    public String getPressure(){
        return pressure;
    }

    /* This method is called by fetchXML */
    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;

        try {
            /* Event of Integer type sores the myParser.getEventType() method's result */
            event = myParser.getEventType();

            /* The event is not end of the document */
            while (event != XmlPullParser.END_DOCUMENT) {
                /* Get a name each column? */
                String name=myParser.getName();

                switch (event){
                    /* The START_TAG is started like <something> */
                    case XmlPullParser.START_TAG:
                        if(name.equals("humidity")){
                            humidity = myParser.getAttributeValue(null, "value");
                        } else if(name.equals("pressure")){
                            pressure = myParser.getAttributeValue(null,"value");
                        } else if(name.equals("temperature")){
                            temperature = myParser.getAttributeValue(null,"value");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    /* The END_TAG is started like </somthing> */
                    case XmlPullParser.END_TAG:
                        if(name.equals("country")){
                            country = text;
                        }  else{
                        }
                        break;
                }
                /* Move to next the event */
                event = myParser.next();
            }
            parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* This method is called by MainActivity */
    public void fetchXML(){
        /* Make a thread */
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() { // This thread is begun here
                try {
                    URL url = new URL(urlString); // Store the url address in url
                    /* Make a connection to the internet */
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect(); // Connect to the internet

                    InputStream stream = conn.getInputStream();

                    /* This is important to parse */
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    /* Make the object of XmlPullParser */
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    /* Call the parseXMLAndStoreIt method */
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}