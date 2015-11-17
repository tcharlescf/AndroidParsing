package com.example.tae.parsingexample;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4, ed5;

    private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String url2 = "&mode=xml&appid=2de143494c0b295cca9337e1e96b00e0";
    private HandleXML obj; // Declare the object
    Button b1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Make button and edit texts */
        b1 = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        ed5 = (EditText) findViewById(R.id.editText5);

        /* When the button is pressed */
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ed1.getText().toString();
                String finalUrl = url1 + url + url2;
                ed2.setText(finalUrl);

                obj = new HandleXML(finalUrl); // Make the object of HandleXML
                obj.fetchXML(); // Call the fetchXML method of HandlXML class

                while (obj.parsingComplete);

                /* Set each text on the edit text to show */
                ed2.setText("Country : " + obj.getCountry());
                ed3.setText("Temperature : " + obj.getTemperature());
                ed4.setText("Humidity : " + obj.getHumidity());
                ed5.setText("Pressure : " + obj.getPressure());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}