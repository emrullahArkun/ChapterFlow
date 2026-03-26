package com.example.readflow.sessions.api;

import com.example.readflow.sessions.api.dto.ReadingSessionDto;
import com.example.readflow.sessions.domain.ReadingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ReadingSessionMapper {

    @Mapping(target = "bookId", source = "book.id")
    ReadingSessionDto toDto(ReadingSession session);
}
