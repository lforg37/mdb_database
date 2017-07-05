CREATE TYPE Image_index_methods AS OBJECT
(

STATIC FUNCTION  ODCIIndexCreate(ia ODCIIndexInfo,
parms VARCHAR2,
env ODCIEnv) 
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexCreate(ODCIIndexInfo, java.lang.String, ODCIEnv) return java.math.BigDecimal',


STATIC FUNCTION  ODCIIndexAlter(ia ODCIIndexInfo,
parms IN OUT VARCHAR2,
alter_option NUMBER,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexAlter(ODCIIndexInfo, java.lang.String, java.math.BigDecimal, ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexDrop(ia ODCIIndexInfo,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDrop(DCIIndexInfo, ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexInsert(ia ODCIIndexInfo,
ridlist VARCHAR2,
newvallist VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexInsert(ODCIIndexInfo, java.lang.String, java.lang.String, ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexDelete(ia ODCIIndexInfo,
rid VARCHAR2,
oldval VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDelete(ODCIIndexInfo , java.lang.String , java.lang.String, ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexUpdate(ia ODCIIndexInfo,
rid VARCHAR2,
oldval VARCHAR2,
newval VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexUpdate(ODCIIndexInfo, java.lang.String, java.lang.String,  java.lang.String, ODCIEnv) return java.math.BigDecimal',

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
NAME 'Index.ODCIIndexInsert(ODCIIndexCtx, ODCIIndexInfo, ODCIPredInfo, ODCIQueryInfo, java.math.BigDecimal, java.math.BigDecimal, java.lang.String, ODCIEnv) return java.math.BigDecimal',

MEMBER FUNCTION  ODCIIndexFetch(self IN OUT impltype,
nrows IN NUMBER,
rids OUT ODCIRidList,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDelete(ODCIIndexCtx, java.math.BigDecimal, ODCIRidList, ODCIEnv) return java.math.BigDecimal',

MEMBER FUNCTION  ODCIIndexClose(self IN impltype,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexUpdate(ODCIIndexCtx, ODCIEnv) return java.math.BigDecimal'
);
/

--CREATE TYPE BODY Image_index_methods
--(
--);
--/

DROP INDEXTYPE Image_index;
