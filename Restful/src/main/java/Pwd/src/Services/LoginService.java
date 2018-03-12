package Pwd.src.Services;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import Pwd.src.Cryptographic_Functions.AES;
import Pwd.src.DBConnect.*;
import Pwd.src.Exceptions.UserNotFoundException;

public class LoginService {
	private static String userId;
	private static MongoDBConnect mongoDBConnect;
	private static MongoDatabase mongoDatabase;

	public LoginService() {
		mongoDBConnect = new MongoDBConnect();
		mongoDatabase = mongoDBConnect.getMongoDatabase();
	}

	@SuppressWarnings("finally")
	public static String userAuthenticate(String userInput) {
		boolean validUser = false;
		try {
			// rbYQalHxi70VCuJn4WzqwH2aEC/X/cQiY+YnuYsmIwtBsa/WfkXSAIracCsiriADEDY89sigrpqU+rb/ZgQ+Jw==
			// userId:SHA(PWD)
			String inputCredentials = AES.decrypt(userInput, Constants_PWD.secretKey);
			userId = inputCredentials.substring(0, inputCredentials.indexOf(":"));
			final String password = inputCredentials.substring(inputCredentials.indexOf(":") + 1);// Hashed//SHA-256
			System.out.println(userId + ":" + password);
			// H3968+uduYWjg0elEW2C3sdGIuKH7y4rrAj0ZyohgWI=
			// >db.mycol.update({'title':'MongoDB Overview'},{$set:{'title':'New MongoDB
			// Tutorial'}})
			validUser = checkUser(userId, password);
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		} finally {
			if (validUser) {
				return userId;
			} else {
				return "false";
			}
		}
	}

	@SuppressWarnings("finally")
	public static boolean checkUser(String userId, String password) throws UserNotFoundException {
		MongoCollection<Document> collection = mongoDatabase.getCollection(Constants_PWD.mainCollection);
		boolean userValid = false;
		Bson filter = Filters.eq("userId", userId);
		try {
			Document dbObj = collection.find(filter).first();
			if (dbObj.isEmpty())
				throw new UserNotFoundException("UserIdNotFound");
			if (dbObj.get("userId").toString().equals(userId.toString())) {
				System.out.println("Valid userID");
				if (password.toString().equals(dbObj.get("password").toString())) {
					userValid = true;
					System.out.println("Valid user!");
				}
			}
		} catch (UserNotFoundException e) {
			System.out.println("In checkUser exception:" + e);
		} catch (Exception e) {
			System.out.println("In checkUser exception:" + e.getMessage());
		} finally {
			return userValid;
		}
	}

	public static void main(String[] args) {
		userAuthenticate("rbYQalHxi70VCuJn4WzqwH2aEC/X/cQiY+YnuYsmIwtBsa/WfkXSAIracCsiriADEDY89sigrpqU+rb/ZgQ+Jw==");
	}

	public boolean createUser(String userInput) {
		boolean createdUser = false;
		MongoCollection<Document> collection = mongoDatabase.getCollection(Constants_PWD.mainCollection);
		String inputCredentials = AES.decrypt(userInput, Constants_PWD.secretKey);
		userId = inputCredentials.substring(0, inputCredentials.indexOf(":"));
		final String password = inputCredentials.substring(inputCredentials.indexOf(":") + 1);// Hashed//SHA-256
		Document doc = new Document("userId", userId).append("password", password);
		try {
			Bson filter = Filters.eq("userId", userId);
			Document dbObj = null;
			dbObj = collection.find(filter).first();
			if (dbObj==null) {
				collection.insertOne(doc);
				createdUser = true;
			} else {
				throw new Exception("User Name already exists in the Table. Change userName. In->LoginService->createUser");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return createdUser;
		}
	}
}
