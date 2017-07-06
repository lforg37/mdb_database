CREATE OR REPLACE FUNCTION Similarity (image1 IN VARCHAR2, 
	images2 IN  VARCHAR2, 
	ctx IN SYS.ODCIINDEXCTX, 
	sctx IN OUT IMAGE_INDEX, 
	scanflg IN NUMBER
	) RETURN NUMBER AS
LANGUAGE JAVA
NAME 'Similarity.similarity (java.lang.String, java.lang.String, oracle.ODCI.ODCIIndexCtx, Index[], java.math.BigDecimal) return java.math.BigDecimal';
/

DROP OPERATOR similarityoperator;

CREATE OR REPLACE OPERATOR similarityoperator
BINDING (VARCHAR2, VARCHAR2) RETURN NUMBER 
WITH INDEX CONTEXT, SCAN CONTEXT Image_index
USING Similarity;

DROP INDEXTYPE Image_index_type;

CREATE OR REPLACE INDEXTYPE Image_index_type
FOR similarityoperator (VARCHAR2, VARCHAR2)
USING Image_index
WITH SYSTEM MANAGED STORAGE TABLES;

DROP INDEX Images_Table_Index;

CREATE INDEX Images_Table_Index ON user_mmdb.IMAGES_TABLE(FILE_PATH)
INDEXTYPE IS Image_index_type;
 
exit 0;
