#!/bin/sh
echo "!!!! Start shell "

cd ./deploy/

./uploadconf.sh
./mvckillsimple.sh
sleep 2
./mvcstart_400.sh
sleep 2
./mvcstatus.sh

