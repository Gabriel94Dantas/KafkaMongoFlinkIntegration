package org.example.contexts;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/***
 * Class responsible to create the connection to MongoDB
 * */
public class MongoContext {
    public String getMongoHost(){
        if (System.getenv("MONGO_HOST") != null
                && !System.getenv("MONGO_HOST").isEmpty()){
            return System.getenv("MONGO_HOST");
        }else{
            return "mongodb://localhost:27017";
        }
    }

    public MongoClient getConnection(){
        return MongoClients.create(getMongoHost());
    }
}
