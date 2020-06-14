package com.demoyer.tickerservice.controller;

import com.demoyer.tickerservice.response_builder.TickerServiceResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TickerServiceController {

    private final TickerServiceResponseBuilder tickerServiceResponseBuilder;

    @Autowired
    public TickerServiceController(TickerServiceResponseBuilder tickerServiceResponseBuilder) {
        this.tickerServiceResponseBuilder = tickerServiceResponseBuilder;
    }

    @GetMapping("/getTickerInfoJSON")
    public ResponseEntity<?> getTickerInfoJSON(@RequestParam("ticker") List<String> tickerList) {
        return tickerServiceResponseBuilder.getTickerInfoJSON(tickerList);
    }

    //will create csv files at C://CSVOutput
    @GetMapping("/getTickerInfoCSV")
    public ResponseEntity<?> getTickerInfoCSV(@RequestParam("ticker") List<String> tickerList) {
        return tickerServiceResponseBuilder.getTickerInfoCSV(tickerList);
    }
}
