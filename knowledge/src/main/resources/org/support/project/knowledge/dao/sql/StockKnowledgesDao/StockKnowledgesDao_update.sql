UPDATE STOCK_KNOWLEDGES
SET 
   COMMENT = ?
 , INSERT_USER = ?
 , INSERT_DATETIME = ?
 , UPDATE_USER = ?
 , UPDATE_DATETIME = ?
 , DELETE_FLAG = ?
WHERE 
KNOWLEDGE_ID = ?
 AND STOCK_ID = ?
;
