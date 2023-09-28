INSERT INTO indexes(id, topic) VALUES (1, 'Work');
INSERT INTO indexes(id, topic) VALUES (2, 'Code');

INSERT INTO pages(id, source_id, url, title, index_key, page_rank)
    VALUES(1, 1, 'https://www.linkedin.com', 'LinkedIn', 1, 0.89);
INSERT INTO pages(id, source_id, url, title, index_key, page_rank)
    VALUES(2, 2, 'https://www.glassdoor.es/', 'Glassdoor', 1, 0.242);
INSERT INTO pages(id, source_id, url, title, index_key, page_rank)
    VALUES(3, 3, 'https://github.com/', 'GitHub', 2, 0.5930);
INSERT INTO pages(id, source_id, url, title, index_key, page_rank)
    VALUES(4, 4, 'https://stackoverflow.com/', 'Stack Overflow', 2, 0.5930);
