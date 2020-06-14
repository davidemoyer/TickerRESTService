# TickerRestService
Sample JSON URL: http://localhost:8080/getTickerInfoJSON?ticker=TSLA,MSFT,TRV,TOY,AAPL

Sample CSV URL: http://localhost:8080/getTickerInfoCSV?ticker=TSLA,MSFT,TRV,TOY,AAPL
CSV Files will write to C:\CSVOutput\

Sample:  C:\CSVOutput\TSLADailyAdjusted2020-06-12.csv

API Service only allows for 5 requests/minute and 500 requests/day
https://www.alphavantage.co/

Must add apiKey to application.yml
