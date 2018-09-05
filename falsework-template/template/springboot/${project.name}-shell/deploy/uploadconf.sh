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

	echo "=====开始上传配置${r'$'}{prjName}：${r'$'}{host}"
	scp -r ./${r'$'}{prjName}_conf/ tomcat@${r'$'}{host}:/usr/webser/springboot/
	
done
