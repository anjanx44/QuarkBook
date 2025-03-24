package org.anjanx44.userservice.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.anjanx44.userservice.dto.LoginRequest;
import org.anjanx44.userservice.dto.TokenResponse;
import org.anjanx44.userservice.entity.User;
import org.anjanx44.userservice.service.UserService;

import java.util.Optional;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @POST
    public Response createUser(User user){
        User createdUser = userService.createUser(user);
        return Response.status(Response.Status.CREATED).entity(createdUser).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user") // Only authenticated users with "user" role can access
    public Response getUser(@PathParam("id") Long id) {
        Optional<User> user = userService.getUserById(id);

        if(user.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(user.get()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response updateUser(@PathParam("id") Long id, User  userDetails){
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return Response.ok(updatedUser).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            return userService.deleteUser(id);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return Response.ok(new TokenResponse(token)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }
}
