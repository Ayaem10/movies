package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExampleResource {

  @Inject
    movieRepository movierepository;

    @GET
    public Response getall(){
        List<movie> movies = movierepository.listAll();
        return Response.ok(movies).build();
    }
    @GET
    @Path("{id}")
    public Response getbyid(@PathParam("id")  Long id){
        return movierepository.findByIdOptional(id)
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(NOT_FOUND).build());

    }

    @GET
    @Path("title/{title}")
    public Response getbytitle(@PathParam("title") String title){
        List<movie> movies = movierepository.findByTitle(title);
        return Response.ok(movies).build();
    }

    @GET
    @Path("country/{country}")
    public Response getbycountry(@PathParam("country") String country){
        List<movie> movies = movierepository.findByCountry(country);
        return Response.ok(movies).build();
    }
    @POST
    @Transactional
    public Response createmovie(movie mov){
        movierepository.persist(mov);
        if (movierepository.isPersistent(mov)) {
            return Response.created(URI.create("/movies/"+mov.getId())).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletebyid(@PathParam("id") Long id){

        boolean deleted = movierepository.deleteById(id);
        return deleted? Response.noContent().build() : Response.status(NOT_FOUND).build();
    }
    @PUT
    @Path("{id}")
    @Transactional
    public Response updatemovie(@PathParam("id") Long id , movie mov){

        return movierepository.findByIdOptional(id)
                .map( m ->{
                    m.setTitle(mov.getTitle());
                    return Response.ok(m).build();
                }).orElse(Response.status(NOT_FOUND).build());

    }

}
