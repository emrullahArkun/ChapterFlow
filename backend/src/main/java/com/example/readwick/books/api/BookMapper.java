package com.example.readwick.books.api;

import com.example.readwick.books.api.dto.BookDto;
import com.example.readwick.books.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BookMapper {

    @Mapping(target = "authorName", source = "author")
    @Mapping(target = "readingGoalProgress", ignore = true)
    BookDto toDto(Book book);
}
