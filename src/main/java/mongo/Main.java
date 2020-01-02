package mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("vehicles");
        MongoCollection mongoCollection = mongoDatabase.getCollection("cars");
        saveData(mongoCollection);
        readData(mongoCollection);
        readDataByParam(mongoCollection, "mark", "bmw");
        updateData(mongoCollection);
        deleteData(mongoCollection, "mark", "fiat");
    }

    private static void readDataByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document(param, value);
        MongoCursor iterator = mongoCollection.find(document).iterator();
        while (iterator.hasNext()) {
            Document doc = (Document) iterator.next();
            System.out.println(doc.toJson());
        }
    }

    private static void readData(MongoCollection mongoCollection) {
        Document first = (Document) mongoCollection.find().first();
        System.out.println(first.toJson());

    }

    private static void saveData(MongoCollection mongoCollection) {
        Document document1 = new Document();
        document1.put("mark", "fiat");
        document1.put("model", "uno");

        Document document2 = new Document();
        document2.put("mark", "skoda");
        document2.put("model", "felicia");
        document2.put("color", Arrays.asList("red", "green", "blue"));

        Document document3 = new Document();
        document3.put("mark", "bmw");
        document3.put("model", "2");

        Document document4 = new Document();
        document4.put("mark", "bmw");
        document4.put("model", "3");

        mongoCollection.insertMany(Arrays.asList(document1, document2, document3, document4));
    }

    private static void deleteData(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document(param, value);
        mongoCollection.deleteOne(document);
    }

    private static void updateData(MongoCollection mongoCollection) {
        Document read = new Document();
        read.put("mark", "bmw");
        Document update = new Document();
        update.put("mark","bmw");
        update.put("model", "2019");
        mongoCollection.replaceOne(read, update);
    }
}