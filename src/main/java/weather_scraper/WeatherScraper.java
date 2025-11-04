package weather_scraper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jsoup.*;
import org.jsoup.nodes.Document;

public class WeatherScraper {
    public static void main(String[] args) {
        String csvFile = "weather_log.csv";
        boolean fileExists = new File(csvFile).exists();

        try {
            String url = "https://www.accuweather.com/vi/vn/ho-chi-minh-city/353981/current-weather/353981?unit=c";
            Document doc = Jsoup.connect(url).get();

            String dayDate = doc.select("div.subnav-pagination div").text();
            String temperature = doc.select("div.display-temp").text();
            String uvIndex = doc.select("div.detail-item:nth-of-type(3) div:nth-of-type(2)").text();
            String wind = doc.select("div.detail-item:nth-of-type(4) div:nth-of-type(2)").text();
            String humidity = doc.select("div.detail-item:nth-of-type(6) div:nth-of-type(2)").text();
            String dewPoint = doc.select("div.detail-item:nth-of-type(7) div:nth-of-type(2)").text();
            String pressure = doc.select("div.detail-item:nth-of-type(8) div:nth-of-type(2)").text();
            String cloudCover = doc.select("div.detail-item:nth-of-type(9) div:nth-of-type(2)").text();
            String visibility = doc.select("div.detail-item:nth-of-type(10) div:nth-of-type(2)").text();
            String ceiling = doc.select("div.detail-item:nth-of-type(11) div:nth-of-type(2)").text();

            // Định dạng thời gian hiện tại
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Ghi file CSV
            try (FileWriter fw = new FileWriter(csvFile, true);
                 PrintWriter pw = new PrintWriter(fw)) {

                // Nếu file chưa tồn tại, thêm header
                if (!fileExists) {
                    pw.println("FullDate,Day,Temperature,UVValue,WindDirection,Humidity,DewPoint,Pressure,Cloud,Visibility,CloudCeiling");
                }

                // Dòng dữ liệu
                pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        time, dayDate, temperature, uvIndex, wind, humidity,
                        dewPoint, pressure, cloudCover, visibility, ceiling);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
