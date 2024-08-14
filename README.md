# This is a prototype of microservice for handling bank transactions.

This microservice allows us to handle transactions, set consumption limits, control excess, store and utilize currency exchange data for conversion into USD.
The main purpose of the microservice is to support control over the bank's clients' expenses.

# Project objectives
1. Receiving and storing data on expense transactions in various currencies.
2. Manage spending limits for different categories (goods and services) in USD.
3. Checking transactions for exceeding established limits.
4. Storing and using exchange rate data to convert transaction amounts.
5. Providing clients with the ability to set new limits and request a list of transactions that have exceeded the limit.

# Technologies and tools

1. Programming language: Java
2. Framework: Spring Boot
3. Database: MySQL
4. ORM: Hibernate/JPA
5. API for getting exchange rates: Twelve Data
6. Database migrations: Flyway
7. Testing tools: JUnit, Mockito

# Database structure
Tables:
Transaction: Contains transaction information (accounts, amount, currency, category, date and time, limit exceeded flag).
Limit: Stores information about spending limits (category, amount, currency, setting date).
ExchangeRate: Stores information about exchange rates for conversion (date, KZT/USD rate, RUB/USD rate). 

## API Documentation

### Set Transaction Limit

**Set Limit:**
````
  POST /limits/set-limit
````
**Request Body:** 
 ```` 
  {
    "limitSum" : 1000.00,
    "expenseCategory" : "product"
  }
 ````
**Response Body:** 
 ```` 
  {
    "id": 1,
    "limitSum": 1000.0,
    "limitDateTime": "2024-08-14 00:00:00 +0500",
    "expenseCategory": "product",
    "limitCurrencyShortname": "USD"
}
 ````
### Transaction
- Save Transaction
````
  POST /transactions/save
````
**Request Body:**
 ```` 
  {
    "accountFrom": 123,
    "accountTo" : 699669,
    "currencyShortname" : "KZT",
    "sum" : 1000010.07,
    "expenseCategory" : "service"
  }
 ````
**Response Body:**
 ```` 
  {
    "id": 1,
    "accountFrom": 123,
    "accountTo": 699669,
    "currencyShortname": "KZT",
    "sum": 1000010.07, 
    "expenseCategory": "service",
    "dateTime": "2024-08-14 00:00:00 +0500",
    "limitExceeded": true
  }
 ````
- Get All Transactions 
````
  GET /transactions/all-transactions
```` 
**Response Body:**
 ```` 
  [
    {
        "accountFrom": "0000000123",
        "accountTo": "0000699669",
        "currencyShortname": "KZT",
        "sum": 1000010.07,
        "expenseCategory": "service",
        "dateTime": "2024-08-14 00:00:00 +0500",
        "limitSum": -1080.02,
        "limitCurrencyShortname": "USD",
        "limitDateTime": "2024-08-14 00:00:00 +0500"
    }
  ]
 ````
- Get Transactions Exceeding Limits
````
  GET /transactions/limit-excess-transaction
```` 
**Response Body:**
 ```` 
[
    {
        "accountFrom": "0000000123",
        "accountTo": "0000699669",
        "currencyShortname": "KZT",
        "sum": 1000010.07,
        "expenseCategory": "service",
        "dateTime": "2024-08-14 00:00:00 +0500",
        "limitSum": -1080.02,
        "limitCurrencyShortname": "USD",
        "limitDateTime": "2024-08-14 00:00:00 +0500"
    }
]
 ````

### Testing
- To run test
````
    mvn test
````