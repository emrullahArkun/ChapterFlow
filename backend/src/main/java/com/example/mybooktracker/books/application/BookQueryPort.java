package com.example.mybooktracker.books.application;

import com.example.mybooktracker.auth.domain.User;
import com.example.mybooktracker.books.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookQueryPort {

    long countByUser(User user);

    long countCompletedByUser(User user);

    List<String> findAllCategoriesByUser(User user);

    List<String> findAllIsbnsByUser(User user);

    List<String> findTopAuthorsByUser(User user, int limit);

    List<String> findTopCategoriesByUser(User user, int limit);

    Optional<Book> findByIdAndUser(Long id, User user);
}
