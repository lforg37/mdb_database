FROM oracle11g-installed
ENV ORACLE_HOME="/opt/oracle/app/product/11.2.0/dbhome_1" 
COPY scripts /scripts

#Compilation des fichiers java et intégration des scripts sql
# à voir avec Paul
RUN cd /scripts/java
