#!/bin/sh
echo "!!!! Start shell "

source ./allconfig.cfg

for host in ${r'$'}{hostjq_keyin[*]}
do
	
	echo "=====开始上传libs至内网服务器：${r'$'}{host}"
	ssh tomcat@${r'$'}{cmdhost_keyout} "scp -r ./common_libs/ tomcat@${r'$'}{host}:/usr/webser/springboot/"
	echo "${r'$'}{host}上传成功"
	
done