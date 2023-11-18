package com.mmokijewski.bikeRentalApp.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface AbstractMapper<D, T> {

    D mapToDto(T t);

    T mapToEntity(D d);

    default List<D> mapToDtos(final List<T> list) {
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    default List<T> mapToEntities(final List<D> list) {
        return list.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}