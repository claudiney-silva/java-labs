package effetivo.restapijavaspringboot.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.request.UserPostRequestBody;
import effetivo.restapijavaspringboot.api.request.UserPutRequestBody;
import effetivo.restapijavaspringboot.api.service.UserService;
import effetivo.restapijavaspringboot.api.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Lista de todos Usuários paginado", description="O tamanho default da página é 20, use o parâmetro size para mudar o valor default", tags ={"Users"})
    public ResponseEntity<Page<User>> findAll(@ParameterObject Pageable pageable) {
        log.info(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " -> lista de usuários");
        return ResponseEntity.ok(userService.listAll(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> findAllNoPageable() {
        log.info(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " -> lista todos usuários");
        return ResponseEntity.ok(userService.listAllNonPageable());
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<User>> findByFirstName(@RequestParam(defaultValue = "") String firstName) {
        log.info(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())
                + " -> lista de usuários por firstName");
        return ResponseEntity.ok(userService.findByFirstName(firstName));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) {
        log.info(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " -> get usuário");
        return ResponseEntity.ok(userService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findByIdAuthenticationPrincipal(@PathVariable long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        log.info(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " -> get usuário");
        return ResponseEntity.ok(userService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> save(@RequestBody @Valid UserPostRequestBody userPostRequestBody) {
        return new ResponseEntity<>(userService.save(userPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful Operation"),
        @ApiResponse(responseCode = "400", description = "When User Does Not Exist in The Database")
    })    
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody UserPutRequestBody userPutRequestBody) {
        userService.replace(userPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}