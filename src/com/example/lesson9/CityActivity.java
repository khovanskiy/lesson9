package com.example.lesson9;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.HashMap;

public class CityActivity extends Activity implements IEventHadler
{
    private long id_city = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cityweather);
        id_city = getIntent().getLongExtra("ID_CITY", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Console.print("Start update");
        City.findById(id_city).addEventListener(this);
        City.findById(id_city).startUpdate();
    }

    public int getImageByType(String type)
    {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("clear", R.drawable.clear);
        map.put("overcastandlightrain", R.drawable.overcastandlightrain);
        map.put("mostlyclear", R.drawable.most);
        map.put("partlycloudy", R.drawable.partlycloudy);
        map.put("cloudy", R.drawable.cloudy);
        map.put("overcast", R.drawable.overcast);
        if (map.containsKey(type))
        {
            return map.get(type);
        }
        else
        {
            return R.drawable.ic_launcher;
        }
    }

    @Override
    public void handleEvent(Event e)
    {
          if (e.type == Event.COMPLETE)
          {
              e.target.removeEventListener(this);
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      TextView tv = (TextView) findViewById(R.id.cityNameLabel);
                      tv.setText(City.findById(id_city).name + "");
                      TextView tv1 = (TextView) findViewById(R.id.cityTemperatureLabel);
                      tv1.setText(City.findById(id_city).temperature + "°");

                      TextView tv2 = (TextView) findViewById(R.id.weatherTypeLabel);
                      tv2.setText(City.findById(id_city).weather_desc+" / "+City.findById(id_city).weather_code);

                      ImageView iv = (ImageView)findViewById(R.id.imageView);
                      iv.setImageResource(getImageByType(City.findById(id_city).weather_code));
                  }
              });
          }
    }
}
