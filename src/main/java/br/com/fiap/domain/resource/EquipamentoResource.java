package br.com.fiap.domain.resource;

import br.com.fiap.domain.entity.Equipamento;
import br.com.fiap.domain.repository.EquipamentoRepository;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.List;


@Path("/equipamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class EquipamentoResource {

    UriInfo uriInfo;

    private EquipamentoRepository repo = EquipamentoRepository.build();

    @GET
    public Response findAll(){
        List<Equipamento> equipamentos = new ArrayList<>();
        equipamentos = repo.findAll();
        return Response.ok(equipamentos).build();
    }

    @GET
    @Path("/{id}")
    public Response findyByid


}
