FROM oracle11g-installed

COPY src /scripts

RUN 	mkdir -p /app/java/build ;\
	export PATH=$PATH:$ORACLE_HOME/bin/:$ORACLE_HOME/jdk/bin/ ;\
	javac -cp /opt/oracle/app/product/11.2.0/dbhome_1/rdbms/jlib/\*.jar \
	      -d /app/java/build $(find ./scripts -name *.java) ; 

ENTRYPOINT ["su", "oracle", "-c", "/scripts/sh/entrypoint_oracle.sh"]	
