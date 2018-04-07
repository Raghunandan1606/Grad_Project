package Pwd.src.Services;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;

import Pwd.src.Cryptographic_Functions.AES;
import Pwd.src.Cryptographic_Functions.SHA;
import Pwd.src.Cryptographic_Functions.SimpleAES;
import Pwd.src.DBConnect.Constants_PWD;
import Pwd.src.DBConnect.MongoDBConnect;
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
			String inputCredentials = SimpleAES.decrypt(userInput, Constants_PWD.secretKey);
			userId = inputCredentials.substring(0, inputCredentials.indexOf(":"));
			String password = inputCredentials.substring(inputCredentials.indexOf(":") + 1);// Hashed//SHA-256
			password = password.substring(0, password.length() - 6);
			System.out.println("PLAIN PASSWORD:"+password);
			password = SHA.getHashedPassword(password);
			System.out.println(userId + ":" + password);
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
		String inputCredentials = SimpleAES.decrypt(userInput, Constants_PWD.secretKey);
		// AES(userId:Password) with secret key 'ssshhhhhhhhhhh!!!!'
		System.out.println("INPUT IS:" + inputCredentials);
		userId = inputCredentials.substring(0, inputCredentials.indexOf(":"));
		String password = inputCredentials.substring(inputCredentials.indexOf(":") + 1);// Hashed//SHA-256
		password = password.substring(0, password.length() - 6);
		System.out.println("Password after decryptign is:" + password);
		password = SHA.getHashedPassword(password);
		Document doc = new Document("userId", userId).append("password", password);
		try {
			Bson filter = Filters.eq("userId", userId);
			Document dbObj = null;
			dbObj = collection.find(filter).first();
			if (dbObj == null) {
				collection.insertOne(doc);
				mongoDatabase.createCollection(userId);
				Document index = new Document("fieldId", 1);
				collection = mongoDatabase.getCollection(userId);
				collection.createIndex(index, new IndexOptions().unique(true));
				createdUser = true;
			} else {
				throw new Exception(
						"User Name already exists in the Table. Change userName. In->LoginService->createUser");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return createdUser;
		}
	}
}
// U2FsdGVkX19qto7HxnXpVXJCmOh2iKqG/C6cAOUpEYbMVV7UcyNoEvKA78FHg18G
// U2FsdGVkX19qto7HxnXpVXJCmOh2iKqG/C6cAOUpEYbMVV7UcyNoEvKA78FHg18G
