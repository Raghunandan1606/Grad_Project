package Pwd.src.Controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Pwd.src.DBConnect.Constants_PWD;
import Pwd.src.Services.JWTAuthenticateService;
import Pwd.src.Services.LoginService;

@Path("login")
public class LoginController {
	private LoginService loginService = new LoginService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		return "gotttt";
	}

	@Path("/createAccount")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean createAccount(@HeaderParam(Constants_PWD.userInput) String userInput) {
		System.out.println(userInput);
		boolean response = loginService.createUser(userInput);
		if (response) {
			System.out.println("Response is " + response);
			return true;
		} else {
			return false;
		}
	}

	@Path("/loginUser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@HeaderParam(Constants_PWD.userInput) String userId) {
		System.out.println(userId);
		String response = loginService.userAuthenticate(userId);
		if (response != "false") {
			System.out.println("Response is " + response);
			return Response.ok(jwtUser(response)).build();
		} else {
			return Response.status(404).entity("ERROR BITCH").type(MediaType.APPLICATION_JSON).build();
		}
	}

	@Path("/loginUserJWT/{userId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String jwtUser(@PathParam(Constants_PWD.jwtToken) String jwtToken) {
		System.out.println("Got here");
		JWTAuthenticateService jwtAuth = new JWTAuthenticateService();
		String res = jwtAuth.createJWT(jwtToken);
		System.out.println(res);
		return res;
	}
}
