CREATE TABLE indexes
(
    id        LONG          PRIMARY KEY AUTO_INCREMENT NOT NULL,
    keywords  VARCHAR(255)  UNIQUE NOT NULL
);

CREATE TABLE pages
(
    id         LONG          PRIMARY KEY AUTO_INCREMENT NOT NULL,
    source_id  LONG          NOT NULL,
    url        VARCHAR(255)  NOT NULL,
    page_rank  DOUBLE        NOT NULL,
    index_key  LONG          NOT NULL,

    FOREIGN KEY (index_key) REFERENCES indexes (id) ON DELETE CASCADE ,
    CONSTRAINT UNIQUE (url, index_key)
);

