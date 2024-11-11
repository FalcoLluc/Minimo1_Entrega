package edu.upc.dsa.services;


import edu.upc.dsa.PuntosInteresManager;
import edu.upc.dsa.PuntosInteresManagerImpl;
import edu.upc.dsa.exceptions.PuntoNoExistenteException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/puntosInteres", description = "Endpoint to Puntos InteresService")
@Path("/puntosInteres")
public class PuntosInteresService {
    private PuntosInteresManager pm;

    public PuntosInteresService() {
        this.pm = PuntosInteresManagerImpl.getInstance();
        if (pm.sizePuntos()==0) {
            this.pm.nuevoPuntoInteres(10,10, ElementType.BRIDGE);
            this.pm.nuevoPuntoInteres(40,20, ElementType.COIN);
        }
        if(pm.sizeUsuarios()==0){
            this.pm.nuevoUsuario("1","Lluc","Fernández","llff2003ae@gmail.com","25/11/2003");
            this.pm.nuevoUsuario("2","Joan","Alvarez","alvarito@gmail.com","12/10/2002");
        }
    }
    @POST
    @ApiOperation(value = "register a new User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Usuario.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/usuarios")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoUsuario(Usuario user) {
        if (user.getId()==null||user.getNombre()==null||user.getApellidos()==null||user.getCorreo()==null||user.getFechaNacimiento()==null)  return Response.status(500).build();
        Usuario u= this.pm.nuevoUsuario(user.getId(),user.getNombre(),user.getApellidos(),user.getCorreo(),user.getFechaNacimiento());
        return Response.status(201).entity(u).build();
    }


    @GET
    @ApiOperation(value = "get all Usuarios", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class, responseContainer="List"),
    })
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios() {
        List<Usuario> users = this.pm.listaUsuarios();
        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(users) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get info of a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 404, message = "User Not Found")
    })
    @Path("/usuarios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        Usuario u;
        try{
            u = pm.infoUsuario(id);
        }
        catch(UsuarioNotFoundException e){
            return Response.status(404).build();
        }
        return Response.status(201).entity(u).build();
    }


    @POST
    @ApiOperation(value = "register a new Punto", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=PuntoInteres.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/puntosInteres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPunto(PuntoInteres punto) {
        PuntoInteres p= this.pm.nuevoPuntoInteres(punto.getHorizontal(),punto.getVertical(),punto.getTipo());
        return Response.status(201).entity(p).build();
    }

    @PUT
    @ApiOperation(value = "registrar paso por Punto", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful",response=PuntoInteres.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Punto No Existente")

    })
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarPunto(PuntoInteres punto,@PathParam("id") String id) {
        try{
            this.pm.registrarPunto(id,punto.getHorizontal(),punto.getVertical());
        }
        catch(PuntoNoExistenteException e1){
            return Response.status(500).build();
        }
        catch(UsuarioNotFoundException e2){
            return Response.status(404).build();
        }
        return Response.status(201).entity(punto).build();
    }

    @GET
    @ApiOperation(value = "get Puntos Pasados User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PuntoInteres.class,responseContainer="List"),
            @ApiResponse(code = 404, message = "User Not Found")
    })
    @Path("/puntosInteres/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntosUsuario(@PathParam("id") String id) {
        List<PuntoInteres> puntos;
        try{
            puntos = pm.getPuntosUsuario(id);
        }
        catch(UsuarioNotFoundException e){
            return Response.status(404).build();
        }
        GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(puntos) {
        };
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "listar usuarios Pasados por punto", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Usuario.class,responseContainer="List"),
            @ApiResponse(code = 500, message = "Punto No Existente")

    })
    @Path("/usuarios/punto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response usuariosPunto(PuntoInteres punto) {
        List<Usuario> usersPunto;
        try{
            usersPunto=this.pm.getUsuariosPorPunto(punto.getHorizontal(),punto.getVertical());
        }
        catch(PuntoNoExistenteException e){
            return Response.status(500).build();
        }
        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usersPunto) {
        };
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "obtener puntos de un tipo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=PuntoInteres.class,responseContainer="List"),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("puntosInteres/tipo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response puntosTipo(PuntoInteres punto) {
        List<PuntoInteres> p= this.pm.getPuntosPorTipo(punto.getTipo());
        GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(p) {
        };
        return Response.status(201).entity(entity).build();
    }
}
