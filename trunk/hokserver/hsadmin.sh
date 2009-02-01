#!/bin/sh -f
#
set cmd=`readlink -f $0`
set dir=$cmd:h

java -cp $dir/target/executable-netbeans.dir/hokserver-1.0-SNAPSHOT.jar citibob.hokserver.admin.ConfigAdmin $*
