#!/bin/bash

DUMPFILE=dumpSticklebackdb`date +%F`
DUMPDIRECTORY=/home/ndunn/DATABASE_BACKUPS

cd $DUMPDIRECTORY
pg_dump -U postgres stickleback_production > $DUMPFILE.sql 
zip $DUMPFILE.zip $DUMPFILE.sql 
rm -f $DUMPFILE.sql 

