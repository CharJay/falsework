#!/bin/sh
echo "!!!! Start shell "

source ./allconfig.cfg

for host in ${r'$'}{hostjq_keyin[*]}
do
	
	echo "=====开始上传${r'$'}{warName}至内网服务器：${r'$'}{host}"
	ssh tomcat@${r'$'}{cmdhost_keyout} "scp /usr/webser/springboot/${r'$'}{warName}  ${r'$'}{user}@${r'$'}{host}:/usr/webser/springboot/"
	echo "${r'$'}{host}上传成功"

done

