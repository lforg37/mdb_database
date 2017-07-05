CREATE TYPE Image_index_methods AS OBJECT
(

STATIC FUNCTION  ODCIIndexCreate(ia ODCIIndexInfo,
parms VARCHAR2,
env ODCIEnv) 
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexCreate(ODCIIndexInfo info, java.lang.String params, ODCIEnv env) return java.math.BigDecimal',


STATIC FUNCTION  ODCIIndexAlter(ia ODCIIndexInfo,
parms IN OUT VARCHAR2,
alter_option NUMBER,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexAlter(ODCIIndexInfo info, java.lang.String params, int alter_options, ODCIEnv env) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexDrop(ia ODCIIndexInfo,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDrop(DCIIndexInfo info, ODCIEnv env) return int',

STATIC FUNCTION  ODCIIndexInsert(ia ODCIIndexInfo,
ridlist VARCHAR2,
newvallist VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexInsert(ODCIIndexInfo info, java.lang.String rid, java.lang.String newval, ODCIEnv env) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexDelete(ia ODCIIndexInfo,
rid VARCHAR2,
oldval VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDelete(ODCIIndexInfo info, java.lang.String rid, java.lang.String oldval, ODCIEnv env) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexUpdate(ia ODCIIndexInfo,
rid VARCHAR2,
oldval VARCHAR2,
newval VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexUpdate(ODCIIndexInfo info, java.lang.String rid, java.lang.String oldval, java.lang.String newval, ODCIEnv env) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexStart(sctx IN OUT ODCIIndexCtx,
ia ODCIIndexInfo,
pi ODCIPredInfo,
qi ODCIQueryInfo,
strt NUMBER,
stop NUMBER,
valargs VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexInsert(ODCIIndexCtx sctx, ODCIIndexInfo ia, ODCIPredInfo pi, ODCIQueryInfo qi, float strt, double stop, double valargs, ODCIEnv env) return java.math.BigDecimal',

MEMBER FUNCTION  ODCIIndexFetch(self IN OUT impltype,
nrows IN NUMBER,
rids OUT ODCIRidList,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDelete(ODCIIndexCtx self, int nrows, ODCIRidList rids, ODCIEnv env) return java.math.BigDecimal',

MEMBER FUNCTION  ODCIIndexClose(self IN impltype,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexUpdate(ODCIIndexCtx self, ODCIEnv env) return java.math.BigDecimal'

);

CREATE TYPE BODY Image_index_methods
(
);

DROP INDEXTYPE Image_index;

CREATE OR REPLACE INDEXTYPE Image_index
FOR similarity (VARCHAR2, VARCHAR2)
USING Image_index_methods
WITH SYSTEM MANAGED STORAGE TABLES;
/
