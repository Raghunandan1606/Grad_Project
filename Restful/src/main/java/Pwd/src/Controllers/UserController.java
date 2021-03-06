package Pwd.src.Controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.MongoWriteException;

import Pwd.src.DBConnect.Constants_PWD;
import Pwd.src.Models.Field;
import Pwd.src.Services.JWTAuthenticateService;
import Pwd.src.Services.UserService;

@Path("user")
public class UserController {
	private UserService userService = new UserService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		return "gotttt";
	}
	// http://localhost:8080/Restful/webapi/user/testuser/GetFields

	@Path("/{userId}/GetFields")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFields(@HeaderParam(Constants_PWD.jwtToken) String jwtToken,
			@PathParam(Constants_PWD.userId) String userId, @HeaderParam("type") String requestType) {
		System.out.println("Reached in GetFields");
		System.out.println("JWT:" + jwtToken);
		if (JWTAuthenticateService.validateJWT(jwtToken, userId)) {
			System.out.println(userId);
			Response response = null;
			if (requestType.equals("all")) {
				System.out.println("REQUESTING ALL FIELD DATA");
				List<Field> userFields = userService.getAllFields(userId);
				Gson gson = new Gson();
				String jsonCartList = gson.toJson(userFields);
				System.out.println(jsonCartList);
				response = Response.ok(jsonCartList).build();
			} else if (requestType.equals("fieldId")) {
				System.out.println("REQUESTING ALL FieldID's DATA");
				List<String> fieldIDs = userService.getFieldIds(userId);
				Gson gson = new Gson();
				String jsonCartList = gson.toJson(fieldIDs);
				System.out.println(jsonCartList);
				response = Response.ok(jsonCartList).build();
			}
			return response;// Response.status(200).entity(userFields).build();
		} else {
			return Response.status(400).build();
		}
	}

	// http://localhost:8080/Restful/webapi/user/testuser/addField
	@SuppressWarnings("finally")
	@Path("/{userId}/addField")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addField(@HeaderParam(Constants_PWD.jwtToken) String jwtToken,
			@PathParam(Constants_PWD.userId) String userId, String bodystring) throws MongoWriteException, Exception {
		Response response = null;
		try {
			Field userField = new Field();
			JSONObject jsonObject = new JSONObject(bodystring);
			userField.setFieldId(jsonObject.get("fieldId").toString());
			userField.setFieldName(jsonObject.get("fieldName").toString());
			userField.setFieldDecrypted(jsonObject.get("passwordDecrypted").toString());
			if (JWTAuthenticateService.validateJWT(jwtToken, userId)) {
				if (userService.addField(userId, userField)) {
					response = Response.ok(true).build();
				}
			} else {
				response = Response.status(Status.UNAUTHORIZED).build();
			}
		} catch (MongoWriteException mx) {
			System.out.println("Mongo exception in addField");
			response = Response.status(Status.PRECONDITION_FAILED).build();
		} catch (Exception ex) {
			System.out.println("Exception in addField");
			response = Response.status(Status.BAD_REQUEST).build();
		} finally {
			return response;
		}
	}

	@Path("/{userId}/modifyField")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifyField(@HeaderParam(Constants_PWD.jwtToken) String jwtToken,
			@PathParam(Constants_PWD.userId) String userId, String userFieldString) throws Exception {
		Response response = null;
		try {
			System.out.println("--------In Modify Field--------");
			System.out.println("JWT:" + jwtToken);
			System.out.println("userId:" + userId);
			System.out.println("Body:" + userFieldString);
			Field userField = new Field();
			JSONObject jsonObject = new JSONObject(userFieldString);
			userField.setFieldId(jsonObject.get("fieldId").toString());
			// userField.setFieldName(jsonObject.get("fieldName").toString());
			userField.setFieldDecrypted(jsonObject.get("passwordDecrypted").toString());
			if (JWTAuthenticateService.validateJWT(jwtToken, userId)) {
				List<Field> userFields = userService.modifyField(userId, userField);
				Gson gson = new Gson();
				String jsonUserFields = gson.toJson(userFields);
				response = Response.ok(jsonUserFields).build();
			} else {
				response = Response.status(Status.UNAUTHORIZED).build();
			}
			return response;
			// } else {
			// return Response.status(400).build();
			// }
		} catch (Exception ex) {
			response = Response.status(Status.BAD_REQUEST).build();
			return response;
		}
	}

	@Path("/{userId}/deleteField")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteField(@HeaderParam(Constants_PWD.jwtToken) String jwtToken,
			@PathParam(Constants_PWD.userId) String userId, String fieldId) {
		System.out.println("--------In Delete Field--------");
		System.out.println("JWT:" + jwtToken);
		System.out.println("userId:" + userId);
		fieldId = fieldId.substring(1, fieldId.length() - 1);
		System.out.println("Body:" + fieldId);
		if (userService.deleteField(userId, fieldId))
			return Response.status(Status.ACCEPTED).build();// userService.deleteField(userId, fieldId);
		else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
