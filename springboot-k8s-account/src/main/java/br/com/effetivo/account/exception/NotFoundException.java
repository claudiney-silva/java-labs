package br.com.effetivo.account.exception;

import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends /*RuntimeException {*/ ResponseStatusException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
        //super(message);
    }

    // @Override
    // public HttpHeaders getResponseHeaders() {
    //     // return response headers
    // }    
}
