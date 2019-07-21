cd /root/svn/demo
docker image rm -f springboot-app
docker build --force-rm -t springboot-app .
docker container rm -f springboot-app
docker run -d -p 3333:8080 -u root -e TZ="Aisa/Shanghai" --name springboot-app springboot-app
