FROM maven:3.6.1-jdk-8
WORKDIR /app
COPY . .
RUN mvn clean package
# ENTRYPOINT ["mvn", "start"]
