package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import database.RedisDatabase;
import model.Line;

@Path("/line")
public class LineService {
	RedisDatabase redis;
	public LineService() {
		redis=new RedisDatabase();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLines() {
		return Response.ok(redis.getAllLines()).build();
	}
	
	@GET
	@Path("/{sign}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam ("sign") String signOfStation) {
		String lines=redis.getLineFromDatbase(signOfStation);
		if(lines!="") {
			return Response.status(200).entity(lines).build();
		}
		else {
			return Response.status(404
					).build();
		}
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Line line) {
//		if(redis.addLineInDatabase(line)) {
//			System.out.println("added");
//			return Response.status(200).entity(line).build();
//		}
//		else {
//			System.out.println("not added");
//			return Response.status(500).entity("Greska pri dodavanju linije.").build();
//		}
		return Response.ok(line).build();
	}
	
}
