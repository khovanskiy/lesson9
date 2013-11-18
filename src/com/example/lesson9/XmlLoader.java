package com.example.lesson9;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class XmlLoader extends Thread implements IEventDispatcher
{
    private HttpClient client = new DefaultHttpClient();
    private EventDispatcher event_pull;
    private String path;

    public XmlLoader(String path)
    {
        this.path = path;
        event_pull = new EventDispatcher();
    }

    @Override
    public void run() {
        super.run();
        try
        {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document result = db.parse(connection.getInputStream());
            Event event = new Event(this, Event.COMPLETE);
            event.data.put("document", result);
            dispatchEvent(event);
        }
        catch (Exception e)
        {
            Log.i("CONSOLE", "ERROR", e);
            dispatchEvent(new Event(this, Event.ERROR));
        }
    }

    @Override
    public void addEventListener(IEventHadler listener) {
        event_pull.addEventListener(listener);
    }

    @Override
    public void removeEventListener(IEventHadler listener) {
        event_pull.removeEventListener(listener);
    }

    @Override
    public void dispatchEvent(Event e) {
        event_pull.dispatchEvent(e);
    }
}
