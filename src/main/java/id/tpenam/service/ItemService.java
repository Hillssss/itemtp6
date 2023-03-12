package id.tpenam.service;

import id.tpenam.model.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ItemService {
    public Response get() {
        return Response.status(Response.Status.OK).entity(Item.findAll().list()).build();
    }
    @Transactional
    public Response post(Map<String, Object> request) {
        Item item = new Item();
        item.name = request.get("name").toString();
        item.count =  request.get("count").hashCode();
        item.price =  request.get("price").hashCode();
        item.type = request.get("type").toString();
        item.description = request.get("description").toString();

        item.persist();
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public Response put(Long id, Map<String, Object> request) {
        Item item = Item.findById(id);
        if (item == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        item.name = request.get("name").toString();
        item.count = request.get("count").hashCode();
        item.price = request.get("price").hashCode();
        item.type = request.get("type").toString();
        item.description = request.get("description").toString();


        item.persist();
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
    @Transactional
    public Response delete(Long id){
        Item item = Item.findById(id);
        if(item == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //save to database
        item.delete();

        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }
}
