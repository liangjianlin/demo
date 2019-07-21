cd /root/svn/demo
git pull https://github.com/liangjianlin/demo.git
mvn package
docker container exec ps -ef | grep demo-0.0.1-SNAPSHOT.jar | cut -c 5 | xargs kill -s 9
docker container exec springboot-app rm -f demo-0.0.1-SNAPSHOT.jar
docker container exec springboot-app mv ./target/demo-0.0.1-SNAPSHOT.jar .
docker container exec springboot-app java -jar demo-0.0.1-SNAPSHOT.jar
