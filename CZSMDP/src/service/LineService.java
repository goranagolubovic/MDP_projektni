package service;

import java.util.ArrayList;
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

import com.google.gson.Gson;

import database.RedisDatabase;
import model.Line;

@Path("/line")
public class LineService {
	RedisDatabase redis;
	Gson gson;
	public LineService() {
		redis=new RedisDatabase();
		gson=new Gson();
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
		List<Line> lines=new ArrayList<>();
		lines=redis.getLineFromDatbase(signOfStation);
		if(!lines.isEmpty()) {
			return Response.status(200).entity(lines).build();
		}
		else {
			return Response.status(404
					).build();
		}
	}
	/*@Path("/{sign}")*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Line line/*,@PathParam("sign") String sign*/) {
		/*if(redis.addLineInDatabase(line)) {
			System.out.println("added");
			return Response.status(200).entity(line).build();
		}
		else {
			System.out.println("not added");
			return Response.status(500).entity("Greska pri dodavanju linije.").build();
		}*/
		Line l=gson.fromJson(line.toString(),Line.class);
		System.out.println(l.toString());
		return Response.status(200).entity(l).build();
	}
	
	@PUT
	@Path("/{id}")
	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(Line line, @PathParam("id") String id) {
		if (redis.update(line, id)) {
			return Response.status(200).entity(line).build();
		}
		return Response.status(404).build();
	}
	
}