package Pwd.src.Services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sun.research.ws.wadl.Doc;

import Pwd.src.DBConnect.MongoDBConnect;
import Pwd.src.Models.Field;
import Pwd.src.Restful.AES;

public class UserService {
	// SecretKey==Firstfourlettersofusername++PasswordFieldId(firstfourletters)
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
				field.setFieldDecrypted(AES.decrypt(document.getString("fieldEncrypted"),
						userId.substring(0, 4) + field.getFieldId().substring(0, 4)));
				field.setFieldName(document.getString("fieldName"));
				userFields.add(field);
			}
			return userFields;
		} catch (Exception ex) {
			System.out.println("Exception in GetFields -> userService." + ex.toString());
			return null;
		} finally {
			// mongoDBConnect.CloseDB();
		}

	}

	public boolean addField(String userId, Field userField) {

		try {
			MongoDatabase mongoDatabase = mongoDBConnect.ConnectDB(userType, userId);
			MongoCollection<Document> collection = mongoDatabase.getCollection(userId);
			// String encryptedString = AES.encrypt(originalString, secretKey);
			String secretKey = userId.substring(0, 4) + userField.getFieldId().substring(0, 4);
			userField.setFieldEncrypted(AES.encrypt(userField.getFieldDecrypted(), secretKey));
			System.out.println("Encrypted is " + userField.getFieldEncrypted());
			userField.setFieldDecrypted("");
			Gson gson = new Gson();
			String json = gson.toJson(userField);
			// Parse to bson document and insert
			Document doc = Document.parse(json);
			collection.insertOne(doc);
			return true;
		} catch (MongoWriteException mx) {
			System.out.println("Duplicate FieldId's cannot exist. Please change FieldId");
			return false;
		} catch (Exception ex) {
			System.out.println("Exception while adding Field in userService." + ex.toString());
			ex.printStackTrace();
			return false;
		} finally {
		}

	}

	public List<Field> modifyField(String userId, Field userField) {
		try {
			MongoDatabase mongoDatabase = mongoDBConnect.ConnectDB(userType, userId);
			MongoCollection<Document> collection = mongoDatabase.getCollection(userId);
			BasicDBObject fields = new BasicDBObject();
			BasicDBObject allQuery = new BasicDBObject();
			System.out.println(userField.getFieldId());
			fields.put("fieldId", userField.getFieldId());
			FindIterable<Document> document = collection.find(fields);
			MongoCursor<Document> cursor = document.iterator();
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				String secretKey = userId.substring(0, 4) + ((String) doc.get("fieldId")).substring(0, 4);
				String newEncryptedString = AES.encrypt(userField.getFieldDecrypted(), secretKey);
				BasicDBObject update = new BasicDBObject();
				update.append("$set", new BasicDBObject().append("fieldEncrypted", newEncryptedString));
				collection.updateOne(fields, update);
			}
			return getAllFields(userId);
		} catch (Exception ex) {
			System.out.println("Exception in modifyField->userServer:" + ex.toString());
		}
		return getAllFields(userId);
	}

	public boolean deleteField(String userId, Field userField) {
		try {
			MongoDatabase mongoDatabase = mongoDBConnect.ConnectDB(userType, userId);
			MongoCollection<Document> collection = mongoDatabase.getCollection(userId);
			BasicDBObject filter = new BasicDBObject();
			filter.put("fieldId", userField.getFieldId());
			collection.deleteOne(filter);
			System.out.println("Deleted as directed");
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return false;
	}

}
