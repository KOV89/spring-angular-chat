FROM gradle:7.4-jdk11

WORKDIR /app

COPY . .

RUN gradle build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/chat.jar"]