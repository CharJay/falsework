#!/bin/sh
echo "!!!! Start shell "

source ./allconfig.cfg

hostlist=${r'$'}{hostjq[*]}
if [ ! -n "$1" ] ;then
	echo ""
else
	echo "特定的host: $1" 
	hostlist=("$1")
fi

for host in ${r'$'}{hostlist}
do

	echo "=====查看状态${r'$'}{prjName}至部署服务器：${r'$'}{host}"
	ssh tomcat@$host "ps -ef|grep ${r'$'}{prjName}|grep -v grep|grep -v kill|awk '{print $2}'"
	echo "ok"
	
done