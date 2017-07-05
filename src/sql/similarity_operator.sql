CREATE OR REPLACE FUNCTION user_mmdb.Similarity (image1 VARCHAR2, images2 VARCHAR2, ctx IN SYS.ODCIINDEXCTX) RETURN NUMBER AS
LANGUAGE JAVA
NAME 'Similarity.similarity (java.lang.String, java.lang.String, oracle.ODCI.ODCIIndexCtx) return java.math.BigDecimal';
/

DROP OPERATOR similarity;

CREATE OR REPLACE OPERATOR similarity 
BINDING (VARCHAR2, VARCHAR2) RETURN NUMBER 
WITH INDEX CONTEXT, SCAN CONTEXT Image_index
USING user_mmdb.Similarity;
/

CREATE OR REPLACE INDEXTYPE Image_index
FOR similarity (VARCHAR2, VARCHAR2)
USING Image_index_methods
WITH SYSTEM MANAGED STORAGE TABLES;
/
