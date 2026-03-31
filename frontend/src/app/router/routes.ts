export const ROUTES = {
    HOME: '/',
    SEARCH: '/search',
    MY_BOOKS: '/my-books',
    STATS: '/stats',
    GOALS: '/goals',
    LOGIN: '/login',
    REGISTER: '/register',
    BOOK_STATS: (id) => `/books/${id}/stats`,
    BOOK_SESSION: (id) => `/books/${id}/session`,
};
