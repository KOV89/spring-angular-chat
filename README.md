# spring-angular-chat
Небольшой демонстрационный проект (онлайн-чат).

## Стэк
### backend
- java 11, spring-boot, postgresql, lombok, liquibase
### frontend
- angular 14, ngrx, angular material, eslint, prettier
### общее
- gradle, jwt, rest, websocket, docker

## Запуск
### Приложение
Необходимо наличие java 11 и выше, postgresql
1. Создать пустую базу данных chat_db (по умолчанию нужен логин пароль postgres postgres, настраивается в application.properties)
2. Из корня проекта выполнить команды
```
gradle build
java -jar build/chat.jar  
```
- По умолчанию приложение будет доступно по адресу http://localhost:8080
- Доступный пользователь: Admin пароль: 1234 
- Собранный проект будет находится в build/
### frontend (отдельно)
Из папки frontend выполнить команды
```
npm install
npm start
```
- По умолчанию фронтэнд будет доступен по адресу http://localhost:4200
### docker
Из корня проекта выполнить команду
```
docker-compose up
```
- По умолчанию приложение будет доступен по адресу http://localhost:8080
- Доступный пользователь: Admin пароль: 1234 