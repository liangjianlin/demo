cd /root/svn/demo
git pull https://github.com/liangjianlin/demo.git
mvn package
docker build --force-rm -t springboot-app .
