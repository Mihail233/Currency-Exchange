# Currency Exchange на REST API 
REST API для описания валют и обменных курсов. Позволяет просматривать и редактировать списки валют и обменных курсов, и совершать расчёт конвертации произвольных сумм из одной валюты в другую.

## Technologies / tools used:
- Java Servlets
- JDBC
- SQLite
- Postman
- Maven

## Структура базы данных:
![_C4ZxdoPOTjVZiAAB-Y6L1Pw86p-x2UygamO12haGtRIueTsjFuyx_qo-_f87msD1nDcx9ILsYPXUo0bRgwfjwTr](https://github.com/user-attachments/assets/7cf75161-5c08-48cf-b95f-5a9ce73ec2b1)

## API
## Currencies

### GET `/currencies`
Получение списка валют.

**Пример ответа:**
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

Ответ:
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
Тип тела запроса: application/x-www-form-urlencoded

Поля формы:

`name`  
`code`  
`sign`  

Пример ответа:
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

Пример ответа:
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

Ответ:
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
Тип тела: application/x-www-form-urlencoded

Поля формы:

`baseCurrencyCode`  
`targetCurrencyCode`  
`rate`  

Пример формы:

baseCurrencyCode=USD
targetCurrencyCode=EUR
rate=0.99

Ответ:
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
Тип тела: application/x-www-form-urlencoded

Поля формы:

`rate`  

Пример:
PATCH /exchangeRate/USDRUB

Ответ:
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

Ответ:
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
