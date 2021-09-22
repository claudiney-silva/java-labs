package br.com.effetivo.awslocalstack.service;

import br.com.effetivo.awslocalstack.domain.Todo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final FileStoreService fileStoreService;

    @Override
    public Todo saveTodo(String title, String description, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s", "bucket1", UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStoreService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Todo todo = Todo.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();

        return todo;
    }

    @Override
    public byte[] downloadTodoImage(String path, String file) {
        return fileStoreService.download(path, file);
    }

    @Override
    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(Todo.builder().build());
        //repository.findAll().forEach(todos::add);
        return todos;
    }
}