package br.com.fiap.domain.resource;

import br.com.fiap.domain.entity.Equipamento;
import br.com.fiap.domain.repository.EquipamentoRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Path("/equipamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class EquipamentoResource {

    UriInfo uriInfo;

    private EquipamentoRepository repo = EquipamentoRepository.build();

    @GET
    @Override
    public Response findAll(){
        List<Equipamento> all = service.finAll();


        return Response
                .status(Response.Status.OK)
                .entity(all)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findyByid(@PathParam("id") Long id){
        Equipamento e = service.findById(id);

        if(Objects.isNull(e)) return Response.status(404).build();

        return Response
                .status(Response.Status.OK)
                .entity(e)
                .build();
    }

    @POST
    @Override
    public Response persist(Equipamento equipamento){
        EquipamentoResource service;
        Equipamento persist = service.persist(equipamento);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path(String.valueOf(persist.getId())).build();

        return Response
                .created(uri)
                .entity(persist)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Override
    public Response update(@PathParam("id") Long id, Equipamento equipamento){
        equipamento.setId(id);
        equipamento e = service.update(equipamento);
        if (Objects.isNull(e)) return Response.status(404).build();
        return Response
                .ok()
                .entity(e)
                .build();
    }
    @DELETE
    @Path("/{id}")
    @Override
    public Response delete(@PathParam("id") Long id){
        boolean deleted = service.delete(id);
        if(deleted) return Response.ok().build();
        return Response.status(404).build();
    }


}
