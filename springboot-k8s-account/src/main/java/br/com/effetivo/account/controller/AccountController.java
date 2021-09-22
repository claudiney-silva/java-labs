package br.com.effetivo.account.controller;

import javax.validation.Valid;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import br.com.effetivo.account.dto.request.AccountPostRequestBody;
import br.com.effetivo.account.dto.request.AccountPutRequestBody;
import br.com.effetivo.account.dto.response.AccountResponseBody;
import br.com.effetivo.account.exception.response.ErrorResponse;
import br.com.effetivo.account.mapper.AccountMapper;
import br.com.effetivo.account.model.Account;
import br.com.effetivo.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@Tag(name = "Accounts")
@ApiResponses({
    @ApiResponse(
        responseCode = "500", 
        description = "Internal Server Error",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject("{\"status\": 500,\"message\": \"Internal Server Error\"}")
        )        
    )
})
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(
  methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
  maxAge = 3600,
  allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
  origins = "*" 
)
public class AccountController {
    
    private final AccountService accountService;

    @Operation(summary = "Find all users", description="Find all users in database", responses = {
        @ApiResponse(
            responseCode = "200", 
            description = "List of all users",
            content = {
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AccountResponseBody.class))                    
                )
            }            
        )
    })    
    @GetMapping
    public List<AccountResponseBody> findAll() {
        List<AccountResponseBody> accountList = accountService.findAll().stream()
            .map(account -> AccountMapper.INSTANCE.toAccountResponseBody(account))
            .collect(Collectors.toList()); 
        
        return accountList;
    }

    @Operation(summary = "Find an account", description="Find an account by Id", responses = {
        @ApiResponse(
            responseCode = "200", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseBody.class)),
            description = "Account found"
        ),
        @ApiResponse(
            responseCode = "404", 
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject("{\"status\": 404,\"message\": \"Account not found\"}")
            ),
            description = "When account does not exist in database"
        )
    })
    @GetMapping("{id}")
    public ResponseEntity<AccountResponseBody> findById(
        @Parameter(description = "Id of account to be found", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")     
        @PathVariable("id") UUID id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(AccountMapper.INSTANCE.toAccountResponseBody(account));
    }     

    @Operation(summary = "Create an account", description="Create an new account", responses = {
        @ApiResponse(
            responseCode = "201", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseBody.class)),
            description = "Created account"
        ),
        @ApiResponse(
            responseCode = "422", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
            description = "Validation errors"
        )
    })    
    @PostMapping
    public ResponseEntity<AccountResponseBody> create(@RequestBody @Valid AccountPostRequestBody accountPostRequestBody){
        Account accountToBeCreated = AccountMapper.INSTANCE.toAccount(accountPostRequestBody);
        accountToBeCreated = accountService.create(accountToBeCreated);        
        return new ResponseEntity<>(AccountMapper.INSTANCE.toAccountResponseBody(accountToBeCreated), HttpStatus.CREATED);
    }  

    @Operation(summary = "Update an account", description="Update an account by Id", responses = {
        @ApiResponse(
            responseCode = "200", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseBody.class)),
            description = "Account updated"
        ),
        @ApiResponse(
            responseCode = "404", 
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject("{\"status\": 404,\"message\": \"Account not found\"}")
            ),
            description = "When account does not exist in database"
        ),        
        @ApiResponse(
            responseCode = "422", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
            description = "Validation errors"
        )
    })        
    @PutMapping("{id}")
    public ResponseEntity<AccountResponseBody> update(
        @Parameter(description = "Id of account to be updated", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") 
        @PathVariable(value = "id") UUID id, 
        @RequestBody @Valid AccountPutRequestBody accountPutRequestBody) {
        Account currentAccount = accountService.findById(id); // check if account exists
        Account accountToBeUpdated = AccountMapper.INSTANCE.toAccount(accountPutRequestBody);        
        accountToBeUpdated.setId(id);
        accountToBeUpdated.setCredentials(currentAccount.getCredentials());

        accountToBeUpdated = accountService.update(accountToBeUpdated);
        return ResponseEntity.ok(AccountMapper.INSTANCE.toAccountResponseBody(accountToBeUpdated));
    }

    @Operation(summary = "Delete an account", description="Delete an account by Id", responses = {
        @ApiResponse(
            responseCode = "200", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseBody.class)),
            description = "Account deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject("{\"status\": 404,\"message\": \"Account not found\"}")
            ),
            description = "When account does not exist in database"
        )        
    }) 
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(        
        @Parameter(description = "Id of account to be deleted", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") 
        @PathVariable(value = "id") UUID id) {

        accountService.findById(id).getId(); // check if account exists
        accountService.delete(id);
        return ResponseEntity.ok().build();
    }
}

