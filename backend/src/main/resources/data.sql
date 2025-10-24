INSERT INTO publication (id, title, authors, publisher, stock)
VALUES (1001, 'Introduction to Spring Boot', 'Craig Walls', 'Manning', 3);

INSERT INTO publication (id, title, authors, publisher, stock)
VALUES (1002, 'Clean Architecture', 'Robert C. Martin', 'Pearson', 1);

INSERT INTO publication (id, title, authors, publisher, stock)
VALUES (1003, 'Domain-Driven Design Quickly', 'Eric Evans', 'InfoQ', 2);

INSERT INTO borrower (id, first_name, last_name, email)
VALUES (2001, 'Anna', 'Schulz', 'anna.schulz@example.com');

INSERT INTO borrower (id, first_name, last_name, email)
VALUES (2002, 'Ben', 'Hansen', 'ben.hansen@example.com');

INSERT INTO borrower (id, first_name, last_name, email)
VALUES (2003, 'Clara', 'Meyer', 'clara.meyer@example.com');

-- Active loan (blocks deletion, reduces available stock)
INSERT INTO loan (id, publication_id, borrower_id, issued_at, due_at, returned_at)
VALUES (3001, 1001, 2001, CURRENT_DATE, DATEADD('DAY', 14, CURRENT_DATE), NULL);

-- Returned loan (should not block deletion)
INSERT INTO loan (id, publication_id, borrower_id, issued_at, due_at, returned_at)
VALUES (3002, 1002, 2002, DATEADD('DAY', -10, CURRENT_DATE), DATEADD('DAY', 4, CURRENT_DATE), CURRENT_DATE);

-- Overdue active loan
INSERT INTO loan (id, publication_id, borrower_id, issued_at, due_at, returned_at)
VALUES (3003, 1003, 2003, DATEADD('DAY', -20, CURRENT_DATE), DATEADD('DAY', -6, CURRENT_DATE), NULL);
