package com.demoyer.tickerservice.controller;

import com.demoyer.tickerservice.response_builder.TickerServiceJSONResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@RestController
public class TickerServiceController {

    private final TickerServiceJSONResponseBuilder tickerServiceJSONResponseBuilder;

    @Autowired
    public TickerServiceController(TickerServiceJSONResponseBuilder tickerServiceJSONResponseBuilder) {
        this.tickerServiceJSONResponseBuilder = tickerServiceJSONResponseBuilder;
    }

    @GetMapping("/getTickerInfoJSON")
    public ResponseEntity<?> getTickerInfoJSON(@RequestParam("ticker") List<String> tickerList) {
        return tickerServiceJSONResponseBuilder.getTickerInfoJSON(tickerList);
    }

    //will create multiple csv files simultaneously at C://CSVOutput ONLY WORKS LOCALLY
    @GetMapping("/getTickerInfoCSV")
    public ResponseEntity<?> getTickerInfoCSV(@RequestParam("ticker") List<String> tickerList) {
        return tickerServiceJSONResponseBuilder.getTickerInfoCSV(tickerList);
    }

    //will create and download a single csv file from the browser
    @GetMapping("/downloadTickerInfoCSV")
    public void downloadTickerInfoCSV(@RequestParam("ticker") String ticker,
                                      HttpServletResponse httpServletResponse) {
        tickerServiceJSONResponseBuilder.downloadTickerDataCSV(ticker, httpServletResponse);
    }
}
