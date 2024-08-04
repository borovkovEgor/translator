# Запуск приложения

## 1. Создайте базу данных с именем `translator`.
   ```sql
   CREATE DATABASE translator;
   ```
## 2. Настройте конфигурационный файл
   Откройте файл `application.properties` и укажите параметры подключения к базе данных. Замените `<your_username>` и `<your_password>` на ваши данные для подключения к базе данных. Если ваш PostgreSQL сервер работает на другом порту, замените 5432 на актуальный порт.
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/translator
   spring.datasource.username=<your_username>
   spring.datasource.password=<your_password>
   ```
## 3. Запустите spring приложение
## 4. Запустите приложение в браузере
   Откройте веб-браузер и перейдите по адресу:
   http://localhost:8080/translate