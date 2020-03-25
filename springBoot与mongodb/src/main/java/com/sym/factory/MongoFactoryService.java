package com.sym.factory;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;

/**
 * @author shenym
 * @date 2020/3/25 17:17
 */
@Service
public class MongoFactoryService {

    /**
     * springBoot默认就已经自动配置好
     */
    @Autowired
    private MongoDbFactory mongoFactory;

    public void query(){
        MongoDatabase db = mongoFactory.getDb();
        MongoCollection<Document> collection = db.getCollection("test");
        FindIterable<Document> documents = collection.find();
        documents.iterator().forEachRemaining(doc->{
            String s = doc.toJson();
            System.out.println(s);
        });
    }
}
