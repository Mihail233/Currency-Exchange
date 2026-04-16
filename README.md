# Currency Exchange на REST API 
REST API для описания валют и обменных курсов. Позволяет просматривать и редактировать списки валют и обменных курсов, и совершать расчёт конвертации произвольных сумм из одной валюты в другую.

## Technologies / tools used:
- Java Servlets
- JDBC
- SQLite
- Postman
- HikariCP
- Maven

## Структура базы данных:
![_C4ZxdoPOTjVZiAAB-Y6L1Pw86p-x2UygamO12haGtRIueTsjFuyx_qo-_f87msD1nDcx9ILsYPXUo0bRgwfjwTr](https://github.com/user-attachments/assets/7cf75161-5c08-48cf-b95f-5a9ce73ec2b1)

## Требования
Перед запуском убедись, что у тебя установлены: 

- JDK 25
- Maven
- Apache Tomcat 11
- SQLite JDBC драйвер подтянется через Maven

## FrontEnd
Был взят [отсюда](https://github.com/zhukovsd/currency-exchange-frontend)

## Перед запуском
1. Настрой путь к базе данных
   В файле `src/main/resources/hikari.properties` укажи свой путь к SQLite базе: 

```
jdbcUrl=jdbc:sqlite::resource:CurrencyExchange.db
```

## Сборка проекта
```
mvn clean package
```

После сборки будет создан WAR-файл:
```
target/CurrencyExchange-1.0-SNAPSHOT.war
```

## Локальный запуск
Скопируй WAR в директорию webapps Tomcat:
```
cp target/CurrencyExchange-1.0-SNAPSHOT.war <TOMCAT_HOME>/webapps/
```
После запуска Tomcat приложение будет доступно по адресу:
```
http://localhost:8080/CurrencyExchange-1.0-SNAPSHOT/
```
## Используемые хосты и порты
В процессе локальной разработки и деплоя использовались следующие хосты и порты.

### для Backend
Backend-приложение запускается на Apache Tomcat.

Локальный адрес backend:
```
http://localhost:8080
```
Типичный путь при деплое:
```
http://localhost:8080/CurrencyExchange-1.0-SNAPSHOT/
```
### для Frontend
Frontend поднимался через nginx, обычно на порту `80`.

Локальный адрес frontend:
```
http://localhost
```
или
```
http://localhost:80
```
Взаимодействие в разработке
Frontend и backend были разделены по портам:
```
frontend: localhost:80
backend: localhost:8080
```
Это означает, что frontend отправлял API-запросы на backend по порту `8080`.

## API

### GET `/currencies`
Получение списка валют.

```json
[
  {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  },
  {
    "id": 1,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
  }
]
```

### GET `/currency/{CODE}`

Получение конкретной валюты.

Пример:
GET `/currency/EUR`

```json

{
  "id": 0,
  "name": "Euro",
  "code": "EUR",
  "sign": "€"
}
```

### POST `/currencies`

Добавление новой валюты в базу.
Тип тела запроса: `application/x-www-form-urlencoded`

Поля формы:

`name`  
`code`  
`sign`  

```json
{
  "id": 0,
  "name": "Euro",
  "code": "EUR",
  "sign": "€"
}
```

### GET `/exchangeRates`

Получение списка всех обменных курсов.

```json
[
  {
    "id": 0,
    "baseCurrency": {
      "id": 0,
      "name": "United States dollar",
      "code": "USD",
      "sign": "$"
    },
    "targetCurrency": {
      "id": 1,
      "name": "Euro",
      "code": "EUR",
      "sign": "€"
    },
    "rate": 0.99
  }
]
```

### GET `/exchangeRate/{PAIR}`

Получение конкретного обменного курса.
Валютная пара задаётся подряд идущими кодами валют.

Пример:
GET `/exchangeRate/USDRUB`

```json
{
  "id": 0,
  "baseCurrency": {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 2,
    "name": "Russian Ruble",
    "code": "RUB",
    "sign": "₽"
  },
  "rate": 80
}
```

### POST `/exchangeRates`

Добавление нового обменного курса.
Тип тела: `application/x-www-form-urlencoded`

Поля формы:

`baseCurrencyCode`  
`targetCurrencyCode`  
`rate`  

Пример формы:

`baseCurrencyCode=USD`  
`targetCurrencyCode=EUR`  
`rate=0.99`  

```json
{
  "id": 0,
  "baseCurrency": {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 1,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
  },
  "rate": 0.99
}
```

### PATCH `/exchangeRate/{PAIR}`

Обновление существующего обменного курса.
Тип тела: `application/x-www-form-urlencoded`

Поля формы:

`rate`  

Пример:
PATCH `/exchangeRate/USDRUB`

```json
{
  "id": 0,
  "baseCurrency": {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 2,
    "name": "Russian Ruble",
    "code": "RUB",
    "sign": "₽"
  },
  "rate": 80
}
```

### GET `/exchange`

Расчёт перевода средств между валютами.

Параметры:

`from` — код базовой валюты  
`to` — код целевой валюты  
`amount` — сумма  

Пример запроса:

GET `/exchange?from=USD&to=AUD&amount=10`

```json
{
  "baseCurrency": {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 1,
    "name": "Australian dollar",
    "code": "AUD",
    "sign": "A$"
  },
  "rate": 1.45,
  "amount": 10.00,
  "convertedAmount": 14.50
}
```
#### Логика получения курса

При обмене валюты **A → B** используется один из трёх сценариев:

1. В таблице `ExchangeRates` существует валютная пара `AB` — используется её курс.

2. В таблице `ExchangeRates` существует валютная пара `BA` — используется обратный курс:  
   `AB = 1 / BA`

3. В таблице `ExchangeRates` существуют валютные пары `USD-A` и `USD-B` — курс вычисляется через кросс-курс:  
   `AB = (USD-B) / (USD-A)`
