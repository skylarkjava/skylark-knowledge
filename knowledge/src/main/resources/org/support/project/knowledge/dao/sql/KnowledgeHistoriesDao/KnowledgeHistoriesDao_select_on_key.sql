SELECT * FROM KNOWLEDGE_HISTORIES
 WHERE 
HISTORY_NO = ?
 AND KNOWLEDGE_ID = ?
 AND DELETE_FLAG = 0;
;
