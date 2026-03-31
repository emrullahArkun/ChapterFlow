package com.example.mybooktracker.books.application;

import com.example.mybooktracker.books.domain.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CreateBookCommandMapper {

    public Book toEntity(CreateBookCommand command) {
        if (command == null) {
            return null;
        }

        Book book = new Book();
        book.setAuthor(command.authorName());
        book.setIsbn(command.isbn());
        book.setTitle(command.title());
        book.setPublishYear(command.publishYear());
        book.setCoverUrl(command.coverUrl());
        book.setPageCount(command.pageCount());

        if (command.categories() != null) {
            book.replaceCategories(new ArrayList<>(command.categories()));
        }

        return book;
    }
}
