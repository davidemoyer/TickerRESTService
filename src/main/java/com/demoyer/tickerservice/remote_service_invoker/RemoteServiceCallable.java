package com.demoyer.tickerservice.remote_service_invoker;

import com.demoyer.tickerservice.model.TickerData;
import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.Callable;

public class RemoteServiceCallable implements Callable<TickerDataProviderResponse> {

    private final URI uri;

    RemoteServiceCallable(URI uri) {
        this.uri = uri;
    }

    @Override
    public TickerDataProviderResponse call() {
        TickerDataProviderResponse tickerDataProviderResponse = new TickerDataProviderResponse();
        TickerData tickerData;
        RestTemplate restTemplate = new RestTemplate();

        try {
            System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId());
            tickerData = restTemplate.getForEntity(uri, TickerData.class).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            tickerDataProviderResponse.getOperationStatus().setStatusCode("1");
            tickerDataProviderResponse.getOperationStatus().setStatusMessage("Bad http response from exchange");
            tickerDataProviderResponse.setData(null);
            return tickerDataProviderResponse;
        }
        tickerDataProviderResponse.setData(tickerData);

        return tickerDataProviderResponse;
    }
}
