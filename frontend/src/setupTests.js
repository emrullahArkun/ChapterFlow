import '@testing-library/jest-dom';
import { cleanup } from '@testing-library/react';
import { beforeAll, beforeEach, afterEach, afterAll } from 'vitest';
import { server } from './mocks/server';

beforeAll(() => server.listen());

beforeEach(() => {
    localStorage.clear();
});

afterEach(() => {
    cleanup();
    server.resetHandlers();
});

afterAll(() => server.close());
