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
        RemoteServiceInvoker remoteServiceInvoker = new RemoteServiceInvoker();
        return remoteServiceInvoker.getTickerDataProviderResponse(uri);
    }

}
