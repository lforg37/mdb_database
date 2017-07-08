FROM oracle11g-installed

ENV ORACLE_HOME="/opt/oracle/app/product/11.2.0/dbhome_1/"
ENV ORACLE_SID="orcl"

COPY tnsnames.ora /opt/oracle/app/product/11.2.0/dbhome_1/network/admin/

COPY src /scripts

RUN 	mkdir -p /app/java/build ;\
	export PATH=$PATH:$ORACLE_HOME/bin/:$ORACLE_HOME/jdk/bin/ ;\
	source /scripts/sh/utils.sh ;\
	jl="`listjar $ORACLE_HOME/rdbms/jlib/`:$ORACLE_HOME/jdbc/lib/ojdbc5dms.jar" ;\
	javac -cp $jl -d /app/java/build $(find ./scripts -name *.java) ; 


ENTRYPOINT ["su", "oracle", "-c", "/scripts/sh/entrypoint_oracle.sh"]	
