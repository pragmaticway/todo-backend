package com.fedex.todo.core.service;

import com.fedex.todo.core.model.TODO;
import com.fedex.todo.core.reporsitory.TODOEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper interface to define mapping rules
 *
 * @author Ruslan
 */
@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mappings({
            @Mapping(target = "desc", source = "description")
    })
    TODO getTODO(TODOEntity entity);

    List<TODO> getTODOList(List<TODOEntity> entity);

    @Mapping(target = "description", source = "desc")
    TODOEntity getTODOEntity(TODO apiModel);
}
