package weather_scraper;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.*;
import org.jsoup.nodes.Document;

public class WeatherScraper {
	public static void main(String[] args) {
		try {
			String url = "https://www.accuweather.com/vi/vn/ho-chi-minh-city/353981/current-weather/353981";
			Document doc = Jsoup.connect(url).get();

			// Thứ, ngày tháng
			String dayDate = doc.select("div.subnav-pagination div").text();

			// Các thông số khác (Accuweather thường đặt trong class riêng, bạn cần test
			// trực tiếp HTML)
			String tempRaw = doc.select("div.display-temp").first().ownText().replace("°", "");
			double tempF = Double.parseDouble(tempRaw);
			double tempC = (tempF - 32) * 5.0 / 9.0;
			String temperature = String.format("%.1f°C", tempC);
			
			String uvIndex = doc.select(".detail-item:nth-of-type(3) div:nth-of-type(2)").text();
			String wind = doc.select("div.detail-item:nth-of-type(4) div:nth-of-type(2)").text();
			String humidity = doc.select("div.detail-item:nth-of-type(6) div:nth-of-type(2)").text();
			String dewPoint = doc.select("div.detail-item:nth-of-type(7) div:nth-of-type(2)").text();
			String pressure = doc.select("div:nth-of-type(8) div:nth-of-type(2)").text();
			String cloudCover = doc.select("div:nth-of-type(9) div:nth-of-type(2)").text();
			String visibility = doc.select("div:nth-of-type(10) div:nth-of-type(2)").text();
			String ceiling = doc.select("div:nth-of-type(11) div:nth-of-type(2)").text();

			String logLine = String.format(
					"%s | %s | Nhiệt độ: %s | UV: %s | Gió: %s | Độ ẩm: %s | Điểm sương: %s | Khí áp: %s | Mây: %s | Tầm nhìn: %s | Trần mây: %s",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), dayDate,
					temperature, uvIndex, wind, humidity, dewPoint, pressure, cloudCover, visibility, ceiling);

			try (FileWriter fw = new FileWriter("weather_log.txt", true); PrintWriter pw = new PrintWriter(fw)) {
				pw.println(logLine);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

