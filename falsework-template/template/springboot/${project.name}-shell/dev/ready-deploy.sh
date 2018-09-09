#!/bin/sh
echo "!!!! Start shell "

source ./allconfig.cfg

################################
for ins in ${r'$'}{otherIns[*]}
do
	mvn -f ${r'$'}{ins} install -Dmaven.test.skip=true 
done
mvn -f ${r'$'}{root}/pom.xml install -Dmaven.test.skip=true 

################################
if [ -d "../${r'$'}{fileType}" ]; then
	echo "文件夹${r'$'}{fileType}已存在"
else
	mkdir ../${r'$'}{fileType}
fi

rm -rf ../${r'$'}{fileType}/${r'$'}{prjName}.jar
cp -rf  ${r'$'}{root}/${r'$'}{prjName}-mvc/target/${r'$'}{prjName}*.jar  ../${r'$'}{fileType}/${r'$'}{prjName}.jar

################################