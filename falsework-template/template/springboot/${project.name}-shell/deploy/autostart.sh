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

shellcmd="su - tomcat -c \"nohup /usr/java/jdk1.7.0_71/bin/java -Dloader.path=/usr/webser/springboot/${r'$'}{prjName}_libs/ -jar /usr/webser/springboot/${r'$'}{prjName}.jar --spring.config.location=/usr/webser/springboot/${r'$'}{prjName}_conf/application.properties > /dev/null 2>&1 &\""

for host in ${r'$'}{hostlist}
do
	
	echo "=====${r'$'}{prjName}开始设置开机自启动,服务器：${r'$'}{host}"
	ssh root@${r'$'}{host} "echo $shellcmd >> /etc/rc.d/rc.local"
	echo "==设置开机自启动 success"

done