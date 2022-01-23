>How To Start Application
1. Clone from git or extract the zip in a folder
2. Import Gradle Project from IntelliJ and set Project JDK to JDK 11
3. Wait for Gradle to download all dependencies
4. From intelliJ window, select run configuration StartWebServer
5. From gradle window, run "bootRun"
6. To run tests, run "test" from gradle or run "All Tests in Crypto Assignment" from run config window
  ------
To trigger the reconciliation for a job use following API:
- API: http://localhost:8080/reconcile?instrument_name=<INSTRUMENT>&interval=<INTERVAL>
- Sample API: http://localhost:8080/reconcile?instrument_name=BTC_USDT&interval=1m

>Task Details and Understanding 

<h4> Conditions for consistent trade/candle stick data: </h4>

1.  Open price: Matches the price of first trade
2.  Close price: Matches price of last trade
3.  High Price: Matches the highest trade price in a given time bucket
4.  Low price: Matches the lowest trade price in a given time bucket
5. Volume of the candle stick time bucket is sum of all trade quantities

To verify if candle-stick chart is correct:
1. Get all trades
1.1. Exclude all trades before the start time (first candle stick - interval)
1.2. Exclude all trades after the end time (last candle stick)
2. Generate a trade list per time bucket of the chart from trade data
1. Sort the trades
2. Create a map of timestamp to list of Trades
3. The list of trades will be sorted (first trade is open, last trade is the close)
3. Compare the prices and volume of each candle stick point
4. Store the validation result in an object and return through rest-api


>Application Design
1. The request to reconcile the trade vs candle-stick data can be triggered by API
2. The interval and the pair of coin/current will be sent as parameters
3. The endpoints have to be configurable
4. The Interval parameter should have a default
5. How to handle a candle stick if there's no trade in time interval?
5.1 cannot verify this candlestick - should ignore it?
5.2 copy the previous candle stick values
6. The design is a bit synchronous, the result is computed in a synchronous way and sent back
6.1 Can move to a more async way to trigger a job to reconcile and provide a call back URL to fetch results


>Error Handling
1. If trade data is missing, we can still generate a candlestick chart
2. If bad input (pair or interval) is sent, send an error message
3. If data is not available through the API, send an error message
4. How to handle a candle stick if there's no trade in time interval?
4.1 have a ZERO value candlestick => approach chosen in this app
4.2 copy the previous candle stick values


>Assumptions
1. The pair and interval are provided through rest api
2. We assume the candle stick data is sorted and trades are not sorted
3. The month interval is a 30-day month (including weekends and ignoring public holidays)
5. The trades are correct and the items to be verified for consistency will be the candle-stick data
6. Assumption about the candle stick data:
1. No time interval is missing
2. The data is not sorted by ascending timestamp
7. We may not have all trades to verify the consistency of candlestick chart


Current Improvements:
1. Can use an ENUM for time interval instead of strings
2. Need to refactor http client to crypto api to allow for retries
3, Can use the factory pattern to refactor all object creation in src and test to be cleaner
4. We can default the time interval in the Main controller if no time interval is provided
5. The tolerance value for comparing BigDecimals can be moved