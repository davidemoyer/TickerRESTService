package com.demoyer.tickerservice.remote_service_invoker;

import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

//calls external api provider
@Component
public class TickerServiceRemoteServiceThreadRunner {


    public List<TickerDataProviderResponse> getTickerData(List<URI> tickerURIList) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<TickerDataProviderResponse>> futureTickerDataResponseList = new LinkedList<>();
        List<TickerDataProviderResponse> tickerDataProviderResponseList = new LinkedList<>();

        for (URI uri : tickerURIList) {
            RemoteServiceCallable remoteServiceCallable = new RemoteServiceCallable(uri);
            futureTickerDataResponseList.add(executorService.submit(remoteServiceCallable));
        }

        try {
            for (Future<TickerDataProviderResponse> future : futureTickerDataResponseList) {
                //removes entry from list if ticker data does not exist
                tickerDataProviderResponseList.removeIf(
                        tickerDataProviderResponse ->
                                tickerDataProviderResponse.getData().getErrorMessage() != null);

                tickerDataProviderResponseList.add(future.get(3, TimeUnit.SECONDS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //only need shutdown function due to future.get() blocking until threads are complete
        executorService.shutdown();
        return tickerDataProviderResponseList;
    }
}
