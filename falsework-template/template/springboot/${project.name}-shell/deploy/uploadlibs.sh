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
	
	echo "=====开始上传libs ${r'$'}{prjName}：${r'$'}{host}"
	ssh tomcat@${r'$'}{host} "mkdir /usr/webser/springboot/"
	scp -r ./${r'$'}{prjName}_libs/ tomcat@${r'$'}{host}:/usr/webser/springboot/
	echo "====上传libs success"
	
done