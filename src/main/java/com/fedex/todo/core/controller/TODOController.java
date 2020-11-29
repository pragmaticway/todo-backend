package com.fedex.todo.core.controller;

import com.fedex.todo.core.handler.TodoApi;
import com.fedex.todo.core.model.TODO;
import com.fedex.todo.core.service.TODOService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller implements API spec
 *
 * @author Ruslan
 */
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class TODOController implements TodoApi {

    private final TODOService service;

    @Override
    public ResponseEntity<TODO> addTODO(TODO body) {
        return ResponseEntity.ok(service.storeItem(body));
    }

    @Override
    public ResponseEntity<Void> deleteTODO(Long id) {
        if (service.deleteItem(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<List<TODO>> findTODO(@Valid String itemName) {
        return ResponseEntity.ok(service.getItems(itemName));
    }

    @Override
    public ResponseEntity<TODO> getTODOById(Long id) {
        TODO item = service.getItem(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Void> updateTODO(Long id, @Valid TODO body) {
        TODO item = service.getItem(id);
        if (item != null) {
            item.setName(body.getName());
            item.setStatus(body.getStatus());
            item.setDesc(body.getDesc());
            service.storeItem(item);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
