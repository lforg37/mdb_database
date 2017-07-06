whenever sqlerror exit 1;
connect sys/oracle@ORAMDB as sysdba;
select 1 from dual;
exit 0;
