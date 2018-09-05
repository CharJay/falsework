#!/bin/sh
echo "!!!! Start shell "

source ./allconfig.cfg

for host in ${r'$'}{hostjq_keyin[*]}
do
	
	echo "=====开始创建文件夹至内网服务器：${r'$'}{host}"
	ssh tomcat@${r'$'}{cmdhost_keyout} "ssh ${r'$'}{user}@${r'$'}{host} mkdir /usr/webser/springboot/"
	echo "${r'$'}{host}创建文件夹成功"

done

