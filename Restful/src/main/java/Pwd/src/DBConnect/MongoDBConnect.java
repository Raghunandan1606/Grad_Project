package Pwd.src.DBConnect;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Pwd.src.Models.Field;

public class MongoDBConnect {
	static MongoClient mongo = new MongoClient("localhost", 27017);

	public static MongoDatabase ConnectDB(int userType, String collectionName) {

		// Creating a Mongo client
		// userType==1: Admin
		// userType==2: User
		// Creating Credentials
		/*
		 * MongoCredential credential; credential =
		 * MongoCredential.createCredential("sampleUser", "myDb",
		 * "password".toCharArray());
		 */
		String database = "";
		System.out.println("Connected to the database successfully");

		// Accessing the database
		if (userType == 2 || userType == 1)
			database = "PasswordWallet";
		else
			return null;
		MongoDatabase mongoDatabase = mongo.getDatabase(database);
		// System.out.println(mongoDatabase.getCollection("testUser"));
		return mongoDatabase;
	}

	public static void CloseDB() {
		mongo.close();
	}

	public static void main(String[] args) {
		MongoDatabase mongoDatabase = ConnectDB(2, "testuser");
		MongoCollection<Document> collection = mongoDatabase.getCollection("testuser");
		List<Field> userFields = new ArrayList<Field>();
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				Field field = new Field();
				System.out.println(document.getString("fieldId"));
				System.out.println(document.getString("fieldEncrypted"));
				System.out.println(document.getString("fieldName"));
				field.setFieldId(document.getString("fieldId").toString());
				field.setFieldEncrypted(document.getString("fieldEncrypted"));
				field.setFieldName(document.getString("fieldName"));
				userFields.add(field);
			}
		} finally {
			CloseDB();
		}
	}
}
