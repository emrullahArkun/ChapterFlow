package com.example.readwick.sessions.api;

import com.example.readwick.sessions.api.dto.ReadingSessionDto;
import com.example.readwick.sessions.domain.ReadingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ReadingSessionMapper {

    @Mapping(target = "bookId", source = "book.id")
    ReadingSessionDto toDto(ReadingSession session);
}
