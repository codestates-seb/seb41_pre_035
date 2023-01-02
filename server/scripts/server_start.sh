#!/usr/bin/env bash
cd /home/ubuntu/build
If [ ! -d ../logs ] ; then
 sudo mkdir ../logs
fI
sudo nohup java -jar -Dspring.profiles.active=server sof-0.0.1-SNAPSHOT.jar >> ../logs 2>> ../logs << ../logs &