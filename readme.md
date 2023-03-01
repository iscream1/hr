run the following commands in project root:


mvn clean package

docker build -t avinty/hr .

docker-compose -f ./docker/docker-compose.yml up -d
