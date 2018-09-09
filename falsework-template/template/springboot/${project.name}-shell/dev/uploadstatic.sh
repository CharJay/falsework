#!/bin/sh
echo "!!!! Start "

source ./allconfig.cfg

for host in ${r'$'}{hostjq[*]}
do

	echo "=====开始执行资源文件部署至服务器：${r'$'}{host}:${r'$'}{prjName}"
	
	echo "${r'$'}{user}@${r'$'}{host}, location_static_path: ${r'$'}{location_static_path}"
	
	cd ${r'$'}{location_static_path}
	
	zip -r ${r'$'}{prjName}.zip ./*
	echo "删除原有zip，并上传zip包到服务器"
	ssh ${r'$'}{user}@${r'$'}{host} "pwd"
	ssh ${r'$'}{user}@${r'$'}{host} "rm ${r'$'}{prjName}.zip"
	scp ${r'$'}{prjName}.zip ${r'$'}{user}@${r'$'}{host}:~/${r'$'}{prjName}.zip
	
	echo "解压新zip包到指定static位置"
	ssh ${r'$'}{user}@${r'$'}{host} "rm -rf ${r'$'}{static_path}/*"
	ssh ${r'$'}{user}@${r'$'}{host} "unzip -oq ~/${r'$'}{prjName}.zip -d ${r'$'}{static_path}/"
	rm -rf ${r'$'}{prjName}.zip
	
	echo "!!!! done upload-static"

done