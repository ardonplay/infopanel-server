TRUNCATE user_role, page_type, page_element_type, page_content_order, pages, user_role, users CASCADE;


INSERT INTO "page_type" ("name") VALUES ('FOLDER'), ('PAGE');

INSERT INTO "page_element_type" ("name") VALUES ('IMAGE'), ('TEXT');

INSERT INTO "user_role" ("name") VALUES ('ROLE_ADMIN');

INSERT INTO
    "pages" (
    "parent_id",
    "title",
    "type",
    "order_id"
)
VALUES (NULL, 'TEST_FOLDER', 1, 1), (1, 'TEST_PAGE', 2, 1);

INSERT INTO
    "page_content" (
    "element_type",
    "body"
)
VALUES (
           2,
           '{"content": "Hello, this is test"}':: jsonb
       );

INSERT INTO "page_content_order" ("page_id", "content_id", "order_id")
VALUES
    (
        2, 1, 1
    )