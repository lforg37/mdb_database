#!/usr/bin/env bash
set -e
source /assets/colorecho
source ~/.bashrc

alert_log="$ORACLE_BASE/diag/rdbms/orcl/$ORACLE_SID/trace/alert_$ORACLE_SID.log"
listener_log="$ORACLE_BASE/diag/tnslsnr/$HOSTNAME/listener/trace/listener.log"
pfile=$ORACLE_HOME/dbs/init$ORACLE_SID.ora

# monitor $logfile
monitor() {
    tail -F -n 0 $1 | while read line; do echo -e "$2: $line"; done
}


trap_db() {
	trap "echo_red 'Caught SIGTERM signal, shutting down...'; stop" SIGTERM;
	trap "echo_red 'Caught SIGINT signal, shutting down...'; stop" SIGINT;
}

start_db() {
	find /corel-10k -name *.jpg > /corel-10k/images.txt

	echo_yellow "Starting listener..."
	monitor $listener_log listener &
	lsnrctl start | while read line; do echo -e "lsnrctl: $line"; done
	MON_LSNR_PID=$!
	echo_yellow "Starting database..."
	trap_db
	monitor $alert_log alertlog &
	MON_ALERT_PID=$!
	sqlplus / as sysdba <<-EOF |
		pro Starting with pfile='$pfile' ...
		startup;
		alter system register;
		exit 0
	EOF
	until sqlplus /nolog @/scripts/sql/waitoracle.sql 
	do
		echo "DB not ready, waiting another 5 seconds"
		sleep 5;
	done
	
	echo "CLEANING DB..."
	sqlplus sys/oracle@oramdb as sysdba @/scripts/sql/clean.sql
	echo "FILLING DB..."
	sqlplus sys/oracle@oramdb as sysdba @/scripts/sql/fill_database.sql
	
	echo "LOADING JAVA CLASSES"
	$ORACLE_HOME/bin/loadjava -user SYS/oracle@oramdb -verbose -force -resolve /app/java/build/*.class

	echo "Creating index structs..."
	sqlplus sys/oracle@oramdb as sysdba @/scripts/sql/mm_index.sql
	
	echo "WAITING FOR THE LIRE API TO WAKE UP..."
	sleep 10

	echo "Creating similarity op..."
	sqlplus sys/oracle@oramdb as sysdba @/scripts/sql/similarity_operator.sql

	while read line; do echo -e "sqlplus: $line"; done
	wait $MON_ALERT_PID
}

create_db() {
	echo_yellow "Database does not exist. Creating database..."
	date "+%F %T"
	monitor $alert_log alertlog &
	MON_ALERT_PID=$!
	monitor $listener_log listener &
	#lsnrctl start | while read line; do echo -e "lsnrctl: $line"; done
	#MON_LSNR_PID=$!
        echo "START DBCA"
	dbca -silent -createDatabase -responseFile /assets/dbca.rsp
	echo_green "Database created."
	date "+%F %T"
	change_dpdump_dir
        touch $pfile
	trap_db
        kill $MON_ALERT_PID
	#wait $MON_ALERT_PID
}

stop() {
    trap '' SIGINT SIGTERM
	shu_immediate
	echo_yellow "Shutting down listener..."
	lsnrctl stop | while read line; do echo -e "lsnrctl: $line"; done
	kill $MON_ALERT_PID $MON_LSNR_PID
	exit 0
}

shu_immediate() {
	ps -ef | grep ora_pmon | grep -v grep > /dev/null && \
	echo_yellow "Shutting down the database..." && \
	sqlplus / as sysdba <<-EOF |
		set echo on
		shutdown immediate;
		exit 0
	EOF
	while read line; do echo -e "sqlplus: $line"; done
}

change_dpdump_dir () {
	echo_green "Changind dpdump dir to /opt/oracle/dpdump"
	sqlplus / as sysdba <<-EOF |
		create or replace directory data_pump_dir as '/opt/oracle/dpdump';
		commit;
		exit 0
	EOF
	while read line; do echo -e "sqlplus: $line"; done
}

chmod 777 /opt/oracle/dpdump

echo "Checking shared memory..."
df -h | grep "Mounted on" && df -h | egrep --color "^.*/dev/shm" || echo "Shared memory is not mounted."
if [ ! -f $pfile ]; then
  create_db;
fi 
start_db
