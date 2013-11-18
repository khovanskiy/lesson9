package com.example.lesson9;

import android.view.View;

public class TouchEvent extends Event
{
    public static final String CLICK = "click";
    public static final String LONG_CLICK = "long_click";

    public TouchEvent(IEventDispatcher target, String type)
    {
        super(target, type);
    }
}