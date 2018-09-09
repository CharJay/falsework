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

	echo "查看$host===================================="
	ssh tomcat@${r'$'}{host} "tail -500f ${r'$'}{exlogpath}"
	
done
