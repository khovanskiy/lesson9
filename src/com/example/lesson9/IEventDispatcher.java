package com.example.lesson9;

public interface IEventDispatcher {
    void addEventListener(IEventHadler listener);

    void removeEventListener(IEventHadler listener);

    void dispatchEvent(Event e);
}
