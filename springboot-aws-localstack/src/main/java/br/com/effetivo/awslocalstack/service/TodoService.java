package br.com.effetivo.awslocalstack.service;

import br.com.effetivo.awslocalstack.domain.Todo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TodoService {
    Todo saveTodo(String title, String description, MultipartFile file);

    byte[] downloadTodoImage(String path, String file);

    List<Todo> getAllTodos();
}