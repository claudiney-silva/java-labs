package br.com.effetivo.awslocalstack.controller;

import br.com.effetivo.awslocalstack.domain.Todo;
import br.com.effetivo.awslocalstack.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
@AllArgsConstructor
@CrossOrigin("*")
public class TodoController {
    TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
    }

    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Todo> saveTodo(@RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(todoService.saveTodo(title, description, file), HttpStatus.OK);
    }

    @GetMapping(value = "download", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE} )
    public byte[] downloadTodoImage(@RequestParam("path") String path,
                                    @RequestParam("file") String file) {
        return todoService.downloadTodoImage(path, file);
    }
}