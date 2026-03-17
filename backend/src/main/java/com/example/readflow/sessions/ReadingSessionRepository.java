package com.example.readflow.sessions;

import com.example.readflow.auth.User;
import com.example.readflow.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {

    Optional<ReadingSession> findFirstByUserAndStatusInOrderByStartTimeDesc(User user,
            Collection<SessionStatus> statuses);

    List<ReadingSession> findByUserAndBook(User user, Book book);

    void deleteByUserAndBook(User user, Book book);

    @Query("SELECT DISTINCT CAST(s.endTime AS LocalDate) FROM ReadingSession s " +
           "WHERE s.user = :user AND s.status = 'COMPLETED' AND s.endTime IS NOT NULL " +
           "AND CAST(s.endTime AS LocalDate) >= :since " +
           "ORDER BY CAST(s.endTime AS LocalDate) DESC")
    List<LocalDate> findDistinctReadingDays(@Param("user") User user, @Param("since") LocalDate since);
}
