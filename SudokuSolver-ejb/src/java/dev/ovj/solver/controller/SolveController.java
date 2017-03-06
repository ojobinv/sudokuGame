/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.ovj.solver.controller;

import dev.ovj.solver.service.SudokuSolver;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * SolveController is the restful service for solving sudoku,
 * which is available at the path /solve. 
 * @author ovj
 */
@Path("/solve")
@Produces("text/plain")
public class SolveController {
    @EJB
    SudokuSolver sudokuSolver;
    
    @GET
    @Produces("text/plain")
    public String solve(@QueryParam("input") String input){
        JsonObject returnObj = null;
        try{
        String data = input.replace("x", "0");
        String[] dataArray = data.split(",");
        if(dataArray.length!=81)
            throw new Exception("Length is not matching");        
         String msg = sudokuSolver.solve(dataArray);
         returnObj = Json.createObjectBuilder().add("solution",msg).build();
        }
        catch (Exception e){
            returnObj = Json.createObjectBuilder().add("error"," cannot be completed").build();
        }
        return returnObj.toString();
    }
}
