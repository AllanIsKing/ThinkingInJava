package com.allanisking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.allanisking.entity.PersonEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuHui on 2017/8/2.
 */
@Path("/JerseyService")
public class JerseyService {
	private static Map<String, PersonEntity> map = new HashMap<String, PersonEntity>();

	@GET
	@Path("/getAllResource")
	public String getAllResource() throws JsonProcessingException {
		List<PersonEntity> list = new ArrayList<PersonEntity>();
		for (PersonEntity entity : map.values()) {
			list.add(entity);
		}

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(list);
	}

	@GET
	@Path("/getResourceById/{id}")
	public String getResource(@PathParam("id") String id) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map.get(id));
	}

	@POST
	@Path("/addResource/{person}")
	public String addResource(String person) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		PersonEntity entity = mapper.readValue(person, PersonEntity.class);
		map.put(entity.getId(), entity);
		return mapper.writeValueAsString(entity);
	}

	@GET
	@Path("/getRandomResource")
	public String getRandomResource() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		PersonEntity entity = new PersonEntity("NO1", "Joker", "http:///");
		return mapper.writeValueAsString(entity);
	}
}