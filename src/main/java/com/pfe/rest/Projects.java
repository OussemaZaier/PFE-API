package com.pfe.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.pfe.Entity.Project;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("projects")
public class Projects {

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@HeaderParam("CIN") String cin) {
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");  

			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:TEST","SYSTEM","12345");
			String statement="SELECT * FROM PROJECTS WHERE USER_ID="+cin;
	        PreparedStatement preparedStatement = conn.prepareStatement(statement);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				ArrayList<Project> projects = new ArrayList<Project>();
				do {
			        projects.add(new Project(resultSet.getString("NAME_PROJECT"),resultSet.getString("DESCRIPTION_PROJECT")));
			      } while (resultSet.next());
				

			    return Response.ok(projects, MediaType.APPLICATION_JSON).build();
           
            }
			else 
			{
				return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for CIN: " + cin).build();
			}
			
    	}
    
    	
    	catch( Exception e ) {
    		return Response.status(Response.Status.NOT_FOUND).entity("server").build();
    	}
    }
    @PUT
    @Path("/create")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public String create (@HeaderParam("image") String Image) 
    {
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");  

			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:TEST","SYSTEM","12345");
			String statement="insert into TEST (image)" + "values(?)";
	        PreparedStatement preparedStatement = conn.prepareStatement(statement); 

	        preparedStatement.setString(1,Image);
			ResultSet resultSet = preparedStatement.executeQuery();
				 return "succes";
			
    		}
    	catch( Exception e ) {
    		return e.getMessage().toString();
    	}
		
    }
    }