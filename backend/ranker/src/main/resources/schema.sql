CREATE TABLE IF NOT EXISTS indexes
(
    id        INT           AUTO_INCREMENT NOT NULL,
    topic     VARCHAR(255)  NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT UNIQUE (topic)
);

CREATE TABLE IF NOT EXISTS pages
(
    id         INT           AUTO_INCREMENT NOT NULL,
    source_id  INT           NOT NULL,
    url        VARCHAR(255)  NOT NULL,
    title      VARCHAR(255)  NOT NULL,
    page_rank  DOUBLE        NOT NULL,
    index_key  INT           NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (index_key) REFERENCES indexes (id) ON DELETE CASCADE,
    CONSTRAINT UNIQUE (url, index_key)
);

