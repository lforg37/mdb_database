DROP Type Image_index;

CREATE TYPE Image_Index AS OBJECT
(
clxID INTEGER,

STATIC FUNCTION ODCIGETINTERFACES(ifclist OUT SYS.ODCIOBJECTLIST) RETURN NUMBER, 

STATIC FUNCTION  ODCIIndexCreate(ia ODCIIndexInfo,
parms VARCHAR2,
env ODCIEnv) 
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexCreate(oracle.ODCI.ODCIIndexInfo, java.lang.String, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',


STATIC FUNCTION  ODCIIndexAlter(ia ODCIIndexInfo,
parms VARCHAR2,
alter_option NUMBER,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexAlter(oracle.ODCI.ODCIIndexInfo, java.lang.String, java.math.BigDecimal, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexDrop(ia ODCIIndexInfo,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDrop(oracle.ODCI.ODCIIndexInfo, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexInsert(ia ODCIIndexInfo,
ridlist VARCHAR2,
newvallist VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexInsert(oracle.ODCI.ODCIIndexInfo, java.lang.String, java.lang.String, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexDelete(ia ODCIIndexInfo,
rid VARCHAR2,
oldval VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexDelete(oracle.ODCI.ODCIIndexInfo , java.lang.String , java.lang.String, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexUpdate(ia ODCIIndexInfo,
rid VARCHAR2,
oldval VARCHAR2,
newval VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexUpdate(oracle.ODCI.ODCIIndexInfo, java.lang.String, java.lang.String,  java.lang.String, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',

STATIC FUNCTION  ODCIIndexStart(sctx ODCIIndexCtx,
ia ODCIIndexInfo,
pi ODCIPredInfo,
qi ODCIQueryInfo,
strt NUMBER,
stop NUMBER,
valargs VARCHAR2,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexStart(oracle.ODCI.ODCIIndexCtx, oracle.ODCI.ODCIIndexInfo, oracle.ODCI.ODCIPredInfo, oracle.ODCI.ODCIQueryInfo, java.math.BigDecimal, java.math.BigDecimal, java.lang.String, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',

MEMBER FUNCTION  ODCIIndexFetch(sctx ODCIIndexCtx,
nrows NUMBER,
rids ODCIRidList,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexFetch(oracle.ODCI.ODCIIndexCtx, java.math.BigDecimal, oracle.ODCI.ODCIRidList, oracle.ODCI.ODCIEnv) return java.math.BigDecimal',

MEMBER FUNCTION  ODCIIndexClose(sctx ODCIIndexCtx,
env ODCIEnv)
RETURN NUMBER
AS LANGUAGE JAVA 
NAME 'Index.ODCIIndexClose(oracle.ODCI.ODCIIndexCtx, oracle.ODCI.ODCIEnv) return java.math.BigDecimal'
);
/

CREATE OR REPLACE TYPE BODY Image_index IS
STATIC FUNCTION ODCIGetInterfaces
  (ifclist OUT NOCOPY SYS.ODCIOBJECTLIST)
  RETURN NUMBER IS
  BEGIN
  ifclist := sys.ODCIObjectList(sys.ODCIObject('SYS','ODCIINDEX2'));
  RETURN SYS.ODCICONST.SUCCESS;
  END ODCIGetInterfaces;
END;
\
