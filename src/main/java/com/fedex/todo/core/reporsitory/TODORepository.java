package com.fedex.todo.core.reporsitory;

import org.springframework.data.repository.CrudRepository;

/**
 * JPA CRUD repository definition
 *
 * @author Ruslan
 */
public interface TODORepository extends CrudRepository<TODOEntity, Long> {
    TODOEntity findById(long id);
    Iterable<TODOEntity> findByNameContainingIgnoreCase(String name);
}
