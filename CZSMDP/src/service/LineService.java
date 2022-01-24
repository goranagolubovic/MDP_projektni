package service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
		redis = new RedisDatabase();
		gson = new Gson();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLines() {
		return Response.ok(redis.getAllLines()).build();
	}

	@GET
	@Path("/{sign}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("sign") String signOfStation) {
		List<Line> lines = new ArrayList<>();
		lines = redis.getLineFromDatbase(signOfStation);
		if (!lines.isEmpty()) {
			return Response.status(200).entity(lines).build();
		} else {
			return Response.status(404).build();
		}
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(Line line) {
		if (redis.update(line.getSign(), line)) {
			return Response.status(200).entity(line).build();
		}
		return Response.status(404).build();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editLine(Line line) {
		if (redis.update(line.getSign(), line)) {
			return Response.status(200).entity(line).build();
		}
		return Response.status(404).build();
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response deleteLine(@PathParam("id") String id) {
		if (redis.deleteLine(id)) {
			return Response.noContent().build();
		}
		return Response.status(404).build();
	}
}
