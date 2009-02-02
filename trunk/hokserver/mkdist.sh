mkdir target/hokserver
(tar cf - target/executable-netbeans.dir baseconfig bin doc/*.pdf) | (cd target/hokserver; tar xvf -)
cp ../hoklaunch/target/hoklaunch-*.jar target/hokserver/hoklaunch.jar

rm `find target/hoklaunch -name '*~'`

cd target;tar cvfz hokserver.tar.gz hokserver
