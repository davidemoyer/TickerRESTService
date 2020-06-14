package com.demoyer.tickerservice.delegate;

import com.demoyer.tickerservice.model.TickerDataProviderResponse;

import java.util.LinkedList;
import java.util.List;

public class CSVListUtils {

    public List<String[]> createCSVLineList(TickerDataProviderResponse tickerDataProviderResponse) {
        LinkedList<String[]> csvLineList = new LinkedList<>();
        csvLineList.add(new String[]{"open", "high", "low", "adjustedClose", "volume", "shouldSell"});

        tickerDataProviderResponse
                .getData()
                .getTickerInfoMap()
                .forEach((variableDate, infoMap) -> csvLineList.add(
                        new String[]{
                                infoMap.get("1. open"),
                                infoMap.get("2. high"),
                                infoMap.get("3. low"),
                                infoMap.get("5. adjusted close"),
                                infoMap.get("6. volume"),
                                "0"}));

        setAveragePriceOutputLabel(csvLineList);

        //not enough info to assign last row's output label so it's removed
        csvLineList.remove(csvLineList.size() - 1);
        return csvLineList;
    }

    public String buildCSVString(TickerDataProviderResponse tickerDataProviderResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        createCSVLineList(tickerDataProviderResponse).forEach(strings -> {
            stringBuilder.append(String.join(",", strings));
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    public String buildCSVFileName(TickerDataProviderResponse tickerDataProviderResponse) {
        return tickerDataProviderResponse
                .getData()
                .getMetaData()
                .get("2. Symbol")
                + "DailyAdjusted"
                + tickerDataProviderResponse
                .getData()
                .getMetaData()
                .get("3. Last Refreshed")
                + ".csv";
    }

    /*  i = 1 to ignore the first row since it's the labels
 for each row to be added after that get the average value between the high and low
 if it's lower than the next day then set "shouldBuy" to 1 otherwise it will be 0
*/
    private void setAveragePriceOutputLabel(LinkedList<String[]> csvLineList) {
        for (int i = 1; i <= csvLineList.size() - 2; i++) {
            if ((Double.parseDouble(csvLineList.get(i)[1]) + Double.parseDouble(csvLineList.get(i)[2])) / 2
                    < (Double.parseDouble(csvLineList.get(i + 1)[1]) + Double.parseDouble(csvLineList.get(i + 1)[2])) / 2) {
                csvLineList.get(i)[5] = "1";
            }
        }
    }
}
