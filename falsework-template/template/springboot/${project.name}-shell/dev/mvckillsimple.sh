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
	
	echo "kill ${r'$'}{prjName}至 服务器 $host"
	pid=$(ssh root@${r'$'}{host} "ps -ef | grep ${r'$'}{prjName} | grep -v grep | awk  '{print $2}'"| awk '{print $2}')
	ssh root@${r'$'}{host} "kill -9 ${r'$'}{pid}"
	echo "====kill success!"
	
done