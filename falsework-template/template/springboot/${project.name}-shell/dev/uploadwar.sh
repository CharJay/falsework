#!/bin/sh
echo "!!!! Start shell "

source ./allconfig.cfg

hostlist=${r'$'}{hostjq[*]}
if [ ! -n "$1" ] ;then
	echo ""
else
	echo "kill特定的host: $1" 
	hostlist=("$1")
fi

for host in ${r'$'}{hostlist}
do

	echo "=====开始上传${r'$'}{prjName}至部署服务器：${r'$'}{host}"
	
	echo "------上传文件"
	scp  ../jar/${r'$'}{prjName}.${r'$'}{fileType}  ${r'$'}{user}@${r'$'}{host}:~/

	echo "-------删除旧jar 移入新jar"
	echo "删除旧jar ${r'$'}{prjName}"
	ssh tomcat@${r'$'}{host} "rm -rf /usr/webser/springboot/${r'$'}{prjName}.jar"
	
	echo "移入新jar ${r'$'}{prjName}"
	
	ssh tomcat@${r'$'}{host} "cp -rf ~/${r'$'}{prjName}.jar -d /usr/webser/springboot/${r'$'}{prjName}.jar"
	
	echo "-------成功"
done


