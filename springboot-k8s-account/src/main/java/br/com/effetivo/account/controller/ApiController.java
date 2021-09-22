package br.com.effetivo.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.effetivo.account.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Api")
@ApiResponses({
    @ApiResponse(
        responseCode = "500", 
        description = "Internal Server Error",
        content = @Content(
            examples = @ExampleObject("{\"status\": 500,\"message\": \"Internal Server Error\"}"),
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )        
    )
})
@RestController
public class ApiController {

    @Operation(summary = "Check if api is running", description="Check if api is running", responses = {
        @ApiResponse(
            responseCode = "200", 
            description = "Api running"
        )
    })      
    @GetMapping
    public ResponseEntity<Void> root(){
        return ResponseEntity.ok().build();
    }     

    @Operation(summary = "Check if api is running", description="Check if api is running", responses = {
        @ApiResponse(
            responseCode = "200", 
            description = "Api running"
        )
    })    
    @GetMapping("/api")
    public ResponseEntity<Void> api(){
        return ResponseEntity.ok().build();
    }    

}
