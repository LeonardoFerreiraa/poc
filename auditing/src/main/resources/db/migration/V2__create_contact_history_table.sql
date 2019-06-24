CREATE TABLE CONTACT_HISTORY (
    ID                 BIGINT,
    NAME               VARCHAR(255)                NOT NULL,
    EMAIL              VARCHAR(255)                NOT NULL,
    CREATED_AT         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    UPDATED_AT         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    REVISION           INT                         NOT NULL ,
    REVISION_TYPE      SMALLINT                    NOT NULL
)
