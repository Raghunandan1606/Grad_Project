package Pwd.src.Controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Pwd.src.Models.Field;
import Pwd.src.Services.UserService;

@Path("user")
public class UserController {
	private UserService userService = new UserService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		return "gotttt";
	}

	@Path("/{userId}/GetFields")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFields(@PathParam("userId") String userId) {
		System.out.println(userId);
		List<Field> userFields = userService.getAllFields(userId);
		return Response.ok(userFields).build();
	}

	//http://localhost:8080/Restful/webapi/user/testuser/addField
	@Path("/{userId}/addField")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addField(@PathParam("userId") String userId,Field userField) {
		return userService.addField(userId,userField);
		//System.out.println("we received:" +userId.getFieldName());
		//return true;
	}

	@Path("/modifyField")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Field> modifyField(@PathParam("userId") String userId) {
		return userService.modifyField();
	}

	@Path("/deleteField")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteField(@PathParam("userId") String userId) {
		return userService.deleteField();
	}

}
