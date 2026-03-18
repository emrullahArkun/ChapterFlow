package com.example.readflow.discovery;

import com.example.readflow.discovery.dto.SearchResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenLibraryClientTest {

    @Mock
    private RestTemplate restTemplate;

    private OpenLibraryClient openLibraryClient;

    @BeforeEach
    void setUp() {
        openLibraryClient = new OpenLibraryClient(restTemplate, "ReadFlow-Test (test@example.com)");
    }

    private void mockResponse(Map<String, Object> response) {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(response));
    }

    private Map<String, Object> bookDoc(String title, int coverId) {
        return Map.of(
                "title", title,
                "author_name", List.of("Author"),
                "cover_i", coverId);
    }

    private Map<String, Object> bookDocWithIsbn(String title, int coverId, String isbn) {
        return Map.of(
                "title", title,
                "author_name", List.of("Author"),
                "cover_i", coverId,
                "isbn", List.of(isbn));
    }

    @Test
    void getBooksByAuthor_ShouldReturnBooks() {
        mockResponse(Map.of("docs", List.of(bookDoc("Book Title", 12345))));

        var result = openLibraryClient.getBooksByAuthor("Author", 5);
        assertEquals(1, result.size());
        assertEquals("Book Title", result.get(0).title());
    }

    @Test
    void getBooksByCategory_ShouldReturnBooks() {
        mockResponse(Map.of("docs", List.of(bookDoc("Cat Book", 12345))));

        var result = openLibraryClient.getBooksByCategory("Fiction", 5);
        assertEquals(1, result.size());
        assertEquals("Cat Book", result.get(0).title());
    }

    @Test
    void getBooksByQuery_ShouldReturnBooks() {
        mockResponse(Map.of("docs", List.of(bookDoc("Search Book", 12345))));

        var result = openLibraryClient.getBooksByQuery("Java", 5);
        assertEquals(1, result.size());
        assertEquals("Search Book", result.get(0).title());
    }

    @Test
    void fetchBooks_ShouldFilterOutBooksWithoutCover() {
        Map<String, Object> withCover = bookDoc("Has Cover", 12345);
        Map<String, Object> noCover = Map.of("title", "No Cover", "author_name", List.of("Author"));

        mockResponse(Map.of("docs", List.of(withCover, noCover)));

        var result = openLibraryClient.getBooksByQuery("test", 5);
        assertEquals(1, result.size());
        assertEquals("Has Cover", result.get(0).title());
    }

    @Test
    void fetchBooks_ShouldReturnEmpty_WhenResponseNull() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(null));

        var result = openLibraryClient.getBooksByAuthor("Author", 5);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchBooks_ShouldReturnEmpty_WhenNoDocs() {
        mockResponse(Map.of("numFound", 0));

        var result = openLibraryClient.getBooksByAuthor("Author", 5);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchBooks_ShouldReturnEmpty_WhenApiThrows() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map.class)))
                .thenThrow(new RestClientException("API error"));

        var result = openLibraryClient.getBooksByAuthor("Author", 5);
        assertTrue(result.isEmpty());
    }

    @Test
    void mapToDto_ShouldBuildCoverUrl() {
        mockResponse(Map.of("docs", List.of(bookDoc("Book", 99999))));

        var result = openLibraryClient.getBooksByQuery("test", 5);
        assertEquals("https://covers.openlibrary.org/b/id/99999-L.jpg", result.get(0).coverUrl());
    }

    @Test
    void mapToDto_ShouldPreferIsbn13() {
        Map<String, Object> doc = Map.of(
                "title", "ISBN Book",
                "cover_i", 12345,
                "isbn", List.of("0123456789", "9781234567890"));

        mockResponse(Map.of("docs", List.of(doc)));

        var result = openLibraryClient.getBooksByQuery("test", 5);
        assertEquals("9781234567890", result.get(0).isbn());
    }

    @Test
    void searchBooks_ShouldReturnBooksWithTotal() {
        mockResponse(Map.of(
                "numFound", 42,
                "docs", List.of(bookDocWithIsbn("Found Book", 12345, "9781234567890"))));

        SearchResultDto result = openLibraryClient.searchBooks("test", 0, 10);
        assertEquals(42, result.totalItems());
        assertEquals(1, result.items().size());
        assertEquals("Found Book", result.items().get(0).title());
    }

    @Test
    void searchBooks_ShouldReturnEmptyWithTotal_WhenNoDocs() {
        mockResponse(Map.of("numFound", 100));

        SearchResultDto result = openLibraryClient.searchBooks("test", 50, 10);
        assertEquals(100, result.totalItems());
        assertTrue(result.items().isEmpty());
    }

    @Test
    void searchBooks_ShouldReturnEmpty_WhenApiThrows() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map.class)))
                .thenThrow(new RestClientException("API error"));

        SearchResultDto result = openLibraryClient.searchBooks("test", 0, 10);
        assertEquals(0, result.totalItems());
        assertTrue(result.items().isEmpty());
    }

    @Test
    void mapToDto_ShouldHandleMissingOptionalFields() {
        Map<String, Object> doc = Map.of("title", "Minimal", "cover_i", 1);
        mockResponse(Map.of("docs", List.of(doc)));

        var result = openLibraryClient.getBooksByQuery("test", 5);
        assertEquals(1, result.size());
        assertEquals("Minimal", result.get(0).title());
        assertNull(result.get(0).authors());
        assertNull(result.get(0).isbn());
        assertNull(result.get(0).pageCount());
    }
}
