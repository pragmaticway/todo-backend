package com.fedex.todo.core.service;

import com.fedex.todo.core.model.TODO;
import com.fedex.todo.core.reporsitory.TODOEntity;
import com.fedex.todo.core.reporsitory.TODORepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class implements TODOItems business logic and communicates with persistence layer
 *
 * @author Ruslan
 */
@AllArgsConstructor
@Slf4j
@Service
public class TODOService {

    private final ItemMapper mapper;
    private final TODORepository repository;

    public List<TODO> getItems(String nameFilter) {
        return mapper.getTODOList(
                StreamSupport.stream(repository.findByNameContainingIgnoreCase(nameFilter == null ? "" : nameFilter)
                        .spliterator(), false).collect(Collectors.toList())
        );

    }

    public TODO storeItem(TODO item) {
        return mapper.getTODO(repository.save(mapper.getTODOEntity(item)));
    }

    public TODO getItem(Long id) {
        log.debug("Item ID: {}", id);
        Optional<TODOEntity> found = repository.findById(id);
        if (found.isPresent()) {
            log.debug("Item ID: {} was found", id);
            return mapper.getTODO(found.get());
        } else {
            log.warn("Item not found! ID: {}", id);
            return null;
        }
    }

    public boolean deleteItem(Long id) {
        log.debug("Deleting item ID: {}", id);
        try {
            repository.deleteById(id);
            log.info("Item ID: {} was deleted", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.error("Item ID: {} does not exists!", id);
            return false;
        }
    }
}
