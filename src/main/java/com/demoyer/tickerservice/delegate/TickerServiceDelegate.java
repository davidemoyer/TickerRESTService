package com.demoyer.tickerservice.delegate;

import com.demoyer.tickerservice.client.TickerServiceClient;
import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
public class TickerServiceDelegate {

    private final TickerServiceClient tickerServiceClient;

    @Autowired
    public TickerServiceDelegate(TickerServiceClient tickerServiceClient) {
        this.tickerServiceClient = tickerServiceClient;
    }

    public List<TickerDataProviderResponse> getTickerDataJSON(List<String> tickerList) {
        return tickerServiceClient.getTickerData(tickerList);
    }

    public void getTickerDataCSV(List<String> tickerList) {
        List<TickerDataProviderResponse> tickerDataProviderResponseList = tickerServiceClient.getTickerData(tickerList);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        tickerDataProviderResponseList.forEach((tickerDataProviderResponse) ->
                executorService.execute(new CSVRunnable(tickerDataProviderResponse)));

        executorService.shutdown();
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
