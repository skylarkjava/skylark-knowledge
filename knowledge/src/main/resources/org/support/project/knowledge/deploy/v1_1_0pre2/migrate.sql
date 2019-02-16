-- カテゴリ
drop table if exists CATEGORIES cascade;

create table CATEGORIES (
  CATEGORY_ID serial not null
  , CATEGORY_NAME character varying(256) not null
  , CATEGORY_ICON character varying(64)
  , DESCRIPTION character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint CATEGORIES_PKC primary key (CATEGORY_ID)
) ;

comment on table CATEGORIES is 'カテゴリ';
comment on column CATEGORIES.CATEGORY_ID is 'カテゴリID';
comment on column CATEGORIES.CATEGORY_NAME is 'カテゴリ名';
comment on column CATEGORIES.CATEGORY_ICON is 'アイコン';
comment on column CATEGORIES.DESCRIPTION is '説明';
comment on column CATEGORIES.INSERT_USER is '登録ユーザ';
comment on column CATEGORIES.INSERT_DATETIME is '登録日時';
comment on column CATEGORIES.UPDATE_USER is '更新ユーザ';
comment on column CATEGORIES.UPDATE_DATETIME is '更新日時';
comment on column CATEGORIES.DELETE_FLAG is '削除フラグ';

INSERT INTO CATEGORIES ( CATEGORY_ID, CATEGORY_NAME, CATEGORY_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG )
VALUES
(1,'コンピュータとIT', 'fa-bookmark', '',0,'2015-09-09 00:00:00',null,null,0);
INSERT INTO CATEGORIES ( CATEGORY_ID, CATEGORY_NAME, CATEGORY_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG )
VALUES
(2,'ビジネスと経済', 'fa-bookmark', '',0,'2015-09-09 00:00:00',null,null,0);
INSERT INTO CATEGORIES ( CATEGORY_ID, CATEGORY_NAME, CATEGORY_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG )
VALUES
(3,'生活と社会', 'fa-bookmark', '',0,'2015-09-09 00:00:00',null,null,0);
INSERT INTO CATEGORIES ( CATEGORY_ID, CATEGORY_NAME, CATEGORY_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG )
VALUES
(4,'学習と教育', 'fa-bookmark', '',0,'2015-09-09 00:00:00',null,null,0);
INSERT INTO CATEGORIES ( CATEGORY_ID, CATEGORY_NAME, CATEGORY_ICON, DESCRIPTION, INSERT_USER, INSERT_DATETIME, UPDATE_USER, UPDATE_DATETIME, DELETE_FLAG )
VALUES
(5,'スポーツと芸術', 'fa-bookmark', '',0,'2015-09-09 00:00:00',null,null,0);

