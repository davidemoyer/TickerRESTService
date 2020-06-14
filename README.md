# TickerRestService
Sample JSON URL: http://localhost:5000/getTickerInfoJSON?ticker=TSLA,MSFT,AAPL

Only works locally to download multiple csv files
Sample Local generation CSV URL: http://localhost:5000/getTickerInfoCSV?ticker=TSLA,MSFT,TRV,TOY,AAPL

CSV Files will write to C:\CSVOutput\
Sample:  C:\CSVOutput\TSLADailyAdjusted2020-06-12.csv

Download CSV File from Browser
Sample download CSV URL: http://localhost:5000/downloadTickerInfoCSV?ticker=TSLA

API Service only allows for 5 requests/minute and 500 requests/day
https://www.alphavantage.co/

Must add apiKey to application.yml to run locally 
