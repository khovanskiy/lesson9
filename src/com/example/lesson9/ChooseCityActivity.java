package com.example.lesson9;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.Vector;

public class ChooseCityActivity extends Activity implements IEventHadler
{
    private CitiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecity);

        adapter = new CitiesAdapter(this, new Vector<City>());
        adapter.addEventListener(this);

        ListView list_view = (ListView) findViewById(R.id.choosecity_citiesList);
        list_view.setAdapter(adapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        XmlLoader loader = new XmlLoader("http://weather.yandex.ru/static/cities.xml");
        loader.addEventListener(this);
        loader.start();
    }

    public void updateList(Document xml)
    {
        adapter.cities.clear();
        Element root =  xml.getDocumentElement();
        NodeList list = xml.getElementsByTagName("city");
        Database.gi().exec("delete from cities where chosen = 0");
        //Console.print("Update List");
        for (int i = 0; i < list.getLength(); ++i)
        {
            Node current = list.item(i);
            if (current.getNodeName().equals(""))
            {
                continue;
            }
            City city = City.findById(Integer.parseInt(((Element)current).getAttribute("id")));
            String name = current.getNodeName();
            String value = current.getFirstChild().getNodeValue();
            city.name = value;
            //Console.print("City id = "+city.getId() + " " + city.name);
            if (!city.chosen)
            {
                city.flush();
                adapter.cities.add(city);
            }
        }
        Collections.sort(adapter.cities);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void handleEvent(Event e)
    {
        //Console.print("Event type = " + e.type);
        if (e.type == Event.COMPLETE)
        {
             e.target.removeEventListener(this);
             updateList((Document) e.data.get("document"));
        }
        else if (e.type == TouchEvent.CLICK)
        {
            City.findById((Long)e.data.get("ID_CITY")).chosen = true;
            City.findById((Long)e.data.get("ID_CITY")).flush();
            Toast.makeText(this, "You have added a new city", 3000).show();
        }
    }
}