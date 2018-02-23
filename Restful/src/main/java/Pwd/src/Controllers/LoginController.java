package Pwd.src.Controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Pwd.src.Models.Field;
import Pwd.src.Services.LoginService;

@Path("login")
public class LoginController {
	private LoginService loginService = new LoginService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		return "gotttt";
	}

	@Path("/loginUser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@PathParam("userId") String userId) {
		System.out.println(userId);
		//YET TO BE DONE: TODO: List<Field> userFields = userService.getAllFields(userId);
		return Response.ok(userFields).build();
	}

}
