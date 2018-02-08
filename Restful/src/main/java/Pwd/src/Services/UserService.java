package Pwd.src.Services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Pwd.src.DBConnect.MongoDBConnect;
import Pwd.src.Models.Field;

public class UserService {

	private MongoDBConnect mongoDBConnect = new MongoDBConnect();
	private int userType = 2;

	public List<Field> getAllFields(String userId) {
		// TODO Auto-generated method stub
		MongoDatabase mongoDatabase = mongoDBConnect.ConnectDB(userType, userId);
		MongoCollection<Document> collection = mongoDatabase.getCollection(userId);
		List<Field> userFields = new ArrayList<Field>();
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				Field field = new Field();
				field.setFieldId(document.getString("fieldId"));
				field.setFieldEncrypted(document.getString("fieldEncrypted"));
				field.setFieldName(document.getString("fieldName"));
				userFields.add(field);
			}
			return userFields;
		}
		catch(Exception ex)
		{
			System.out.println("Exception in GetFields -> userService.");
			return null;
		}
		finally {
			// mongoDBConnect.CloseDB();
		}
		
	}

	public boolean addField(String userId, Field userField) {

		try {
			MongoDatabase mongoDatabase = mongoDBConnect.ConnectDB(userType, userId);
			MongoCollection<Document> collection = mongoDatabase.getCollection(userId);
			Gson gson = new Gson();
			String json = gson.toJson(userField);
			// Parse to bson document and insert
			Document doc = Document.parse(json);
			collection.insertOne(doc);
			return true;
		} catch (Exception ex) {
			System.out.println("Exception while adding Field in userService.");
			return false;
		} finally {
		}

	}

	public List<Field> modifyField() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteField() {
		// TODO Auto-generated method stub
		return false;
	}

}
