package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Path("/Movies")
public class MovieResource {

    public static List<String> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMovies(){//Returns a JAX-RS response
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Integer countMovies(){
        return movies.size();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createMovie(String newMovie){
        movies.add(newMovie);
        return Response.ok(movies).build();
    }

    @PUT
    @Path("{movieToUpdate}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateMovie(
            @PathParam("movieToUpdate") String movieToUpdate, @QueryParam("movie") String updateMovie){
        movies = movies.stream().map(movie->{
            if(movie.equals(movieToUpdate)){
                    return updateMovie;
            }
            return movie;
        }).collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{movieToDelete}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteMovie(
            @PathParam("movieToDelete") String movieToDelete){
        boolean removed = movies.remove(movieToDelete);
        return removed ? Response.noContent().build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
