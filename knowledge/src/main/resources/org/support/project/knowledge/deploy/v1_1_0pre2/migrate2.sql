ALTER TABLE KNOWLEDGES DROP COLUMN IF EXISTS CATAGORY_ID;
ALTER TABLE KNOWLEDGES ADD COLUMN CATAGORY_ID integer;
comment on column KNOWLEDGES.CATAGORY_ID is 'カテゴリ';

create index IDX_KNOWLEDGES_CATAGORY_ID
  on KNOWLEDGES(CATAGORY_ID);

