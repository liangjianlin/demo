cd /root/svn/demo
git pull https://github.com/liangjianlin/demo.git
mvn package
docker build --force-rm -t springboot-app .
docker run -d -p 8080:8080 -u root -e TZ="Aisa/Shanghai" --name springboot-app springboot-app
