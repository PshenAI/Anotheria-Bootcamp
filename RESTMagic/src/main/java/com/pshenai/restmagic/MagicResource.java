package com.pshenai.restmagic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/api/v1")
public class MagicResource {

    private final MagicSquareRepository squareRepository;
    private final MagicSquareService squareService;

    @Autowired
    public MagicResource(MagicSquareRepository squareRepository, MagicSquareService squareService) {
        this.squareRepository = squareRepository;
        this.squareService = squareService;
    }

    @GET
    @Produces("application/json")
    @Path("/tiles")
    public List<MagicSquareTile> getAllTiles() {
        return squareRepository.findAll();
    }

    @GET
    @Produces("application/json")
    @Path("/tile/{id}")
    public ResponseEntity<MagicSquareTile> getUserById(@PathParam(value = "id") Long squareId) throws NullPointerException {
        MagicSquareTile user = squareRepository.findById(squareId)
                .orElseThrow(() -> new NullPointerException("Tile not found - " + squareId));
        return ResponseEntity.ok().body(user);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/tile")
    public MagicSquareTile createTile(MagicSquareTile squareTile) {
        return squareRepository.save(squareTile);
    }

    @POST
    @Produces("text/plain")
    @Path("/square/{n}")
    public String createSquare(@PathParam(value = "n") Integer squareSize,MagicSquareTile squareTile) {
        try {
            squareService.calculateSquare(squareSize);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return "Square creation successful";
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/tiles/{id}")
    public ResponseEntity<MagicSquareTile> updateTile(@PathParam(value = "id") Long squareId,
                                           @Valid @RequestBody MagicSquareTile squareDetails) throws NullPointerException {
        MagicSquareTile squareTile = squareRepository.findById(squareId)
                .orElseThrow(() -> new NullPointerException("Tile not found - " + squareId));

        squareTile.setSquareNum(squareDetails.getSquareNum());
        squareTile.setTilePos(squareDetails.getTilePos());
        squareTile.setValue(squareDetails.getValue());
        final MagicSquareTile updatedTile = squareRepository.save(squareTile);
        return ResponseEntity.ok(updatedTile);
    }

    @DELETE
    @Produces("text/plain")
    @Path("/tiles/{id}")
    public String deleteTile(@PathParam(value = "id") Long squareId) throws NullPointerException {
        MagicSquareTile squareTile = squareRepository.findById(squareId)
                .orElseThrow(() -> new NullPointerException("Tile not found :: " + squareId));

        squareRepository.delete(squareTile);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted tile by Id: " + squareId, Boolean.TRUE);
        return response.toString();
    }
}
