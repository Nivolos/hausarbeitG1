INSERT INTO publication (id, title, authors, publisher, stock)
VALUES (1001, 'Introduction to Spring Boot', 'Craig Walls', 'Manning', 3);

INSERT INTO borrower (id, first_name, last_name, email)
VALUES (2001, 'Anna', 'Schulz', 'anna.schulz@example.com');

INSERT INTO loan (id, publication_id, borrower_id, issued_at, due_at, returned_at)
VALUES (3001, 1001, 2001, CURRENT_DATE, DATEADD('DAY', 14, CURRENT_DATE), NULL);
