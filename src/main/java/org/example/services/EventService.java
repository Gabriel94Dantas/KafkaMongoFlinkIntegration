package org.example.services;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.contexts.MongoContext;
import org.example.converters.EventConverter;
import org.example.models.Event;

import javax.print.Doc;
import java.util.Map;

/***
 * Class responsible to deal with business logic of events
 * */
public class EventService {
    private MongoClient mongoClient;
    private MongoDatabase eventDatabase;

    public EventService(){
        MongoContext mongoContext = new MongoContext();
        this.mongoClient = mongoContext.getConnection();
        this.eventDatabase = this.mongoClient.getDatabase("eventDB");
    }

    public Event returnEventId(Event event){
        Gson gson = new Gson();
        MongoCollection<Document> collection = this.eventDatabase.getCollection(event.getType());
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", event.getId());

        Document documentReturned = collection.find(whereQuery).first();

        if (documentReturned != null){
            Event eventReturned = EventConverter.jsonToEvent(gson.toJson(documentReturned));
            return eventReturned;
        }else{
            return null;
        }
    }

    public void insertEvent(Event event){
        Gson gson = new Gson();
        MongoCollection<Document> collection = this.eventDatabase.getCollection(event.getType());

        Document document = gson.fromJson(EventConverter.eventToJson(event), Document.class);
        collection.insertOne(document);
    }
}
