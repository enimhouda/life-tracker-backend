package com.life.tracker.mapper;

import com.life.tracker.model.ZielEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.Ziel;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ZielMapper {

    @Mapping(source = "titel", target = "title")
    @Mapping(source = "beschreibung", target = "description")
    Ziel toModel(ZielEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(source = "title", target = "titel")
    @Mapping(source = "description", target = "beschreibung")
    ZielEntity toEntity(Ziel model);

    List<Ziel> toModelList(List<ZielEntity> entities);

    List<ZielEntity> toEntityList(List<Ziel> models);
}

