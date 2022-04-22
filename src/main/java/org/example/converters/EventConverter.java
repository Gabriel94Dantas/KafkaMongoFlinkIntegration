package org.example.converters;

import com.google.gson.Gson;
import org.example.models.Event;

/**
 * Class responsible to convert Event to Json and vice versa.
 * */
public class EventConverter {

    public static String eventToJson(Event event){
        Gson gson = new Gson();
        String jsonString = gson.toJson(event);
        return jsonString;
    }

    public static Event jsonToEvent(String jsonString){
        Gson gson = new Gson();
        Event event = gson.fromJson(jsonString, Event.class);
        return event;
    }

}
