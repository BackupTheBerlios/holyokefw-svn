mkdir target/hokserver
(tar cf - target/executable-netbeans.dir baseconfig bin doc/*.pdf) | (cd target/hokserver; tar xvf -)
cp ../hoklaunch/target/hoklaunch-*.jar target/hokserver/hoklaunch.jar

rm `find target/hokserver -name '*~'`
rm -rf `find target/hokserver -name .svn`

cd target;tar cvfz hokserver.tar.gz hokserver
