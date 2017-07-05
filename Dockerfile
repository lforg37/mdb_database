FROM oracle11g-installed

COPY src /scripts

ENV ORACLE_HOME="/opt/oracle/app/product/11.2.0/dbhome_1/"

RUN 	mkdir -p /app/java/build ;\
	export PATH=$PATH:$ORACLE_HOME/bin/:$ORACLE_HOME/jdk/bin/ ;\
	source /scripts/sh/utils.sh ;\
	jl=`listjar $ORACLE_HOME/rdbms/jlib/` ;\
	javac -cp $jl -d /app/java/build $(find ./scripts -name *.java) ; 

ENTRYPOINT ["su", "oracle", "-c", "/scripts/sh/entrypoint_oracle.sh"]	
