package com.interviewTask.event.util.converter;

public interface Converter<E,D> {
    E convertToEntity(D dto);
    D convertToDto(E entity);
}
