package com.demoyer.tickerservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TickerDataProviderResponse extends Response<TickerData> {
}
