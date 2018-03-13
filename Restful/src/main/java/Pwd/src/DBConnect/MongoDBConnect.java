package Pwd.src.DBConnect;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import Pwd.src.Cryptographic_Functions.SHA;
import Pwd.src.Models.Field;

public class MongoDBConnect {
	static MongoClient mongo = new MongoClient("localhost", 27017);
	private static MongoDatabase mongoDatabase;

	public MongoDBConnect() {
		mongoDatabase = mongo.getDatabase(Constants_PWD.database);
	}

	public static MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}

	public static void CloseDB() {
		mongo.close();
	}

	public static void main(String[] args) {
		MongoDatabase mongoDatabase = getMongoDatabase();
		MongoCollection<Document> collection = mongoDatabase.getCollection("testuser");
		List<Field> userFields = new ArrayList<Field>();
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				Field field = new Field();
				System.out.println(document.getString(Constants_PWD.fieldId));
				System.out.println(document.getString(Constants_PWD.fieldEncrypted));
				System.out.println(document.getString(Constants_PWD.fieldName));
				field.setFieldId(document.getString(Constants_PWD.fieldId).toString());
				field.setFieldEncrypted(document.getString(Constants_PWD.fieldEncrypted));
				field.setFieldName(document.getString(Constants_PWD.fieldName));
				userFields.add(field);
			}
		} finally {
			CloseDB();
		}
	}
}
