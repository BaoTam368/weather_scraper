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

			String tempRaw = doc.select("div.display-temp").first().ownText().replace("°", "");
			double tempF = Double.parseDouble(tempRaw);
			double tempC = (tempF - 32) * 5.0 / 9.0;
			String temperature = String.format("%.1f°C", tempC);
			
			String uvIndex = doc.select("div.detail-item:nth-of-type(3) div:nth-of-type(2)").text();
	
			String windRaw = doc.select("div.detail-item:nth-of-type(4) div:nth-of-type(2)").text();
			// Ví dụ: "TTN 5 mi/h"

			// Tách thành hướng và tốc độ
			String[] parts = windRaw.split(" ");
			String direction = parts[0];      // TTN
			double speed = Double.parseDouble(parts[1]); // 5
			String unit = parts[2];           // mi/h

			// Nếu là mi/h thì convert
			if (unit.contains("mi")) {
			    speed = speed * 1.60934;
			    unit = "km/h";
			}

			String wind = String.format("%s %.1f %s", direction, speed, unit);
			
			String humidity = doc.select("div.detail-item:nth-of-type(6) div:nth-of-type(2)").text();
			
			tempRaw = doc.select("div.detail-item:nth-of-type(7) div:nth-of-type(2)").first().ownText().replace("°", "");
			tempF = Double.parseDouble(tempRaw);
			tempC = (tempF - 32) * 5.0 / 9.0;
			String dewPoint = String.format("%.1f°C", tempC);
			
			// tìm block chứa chữ "Khí áp"
			Element pressureBlock = doc.select("div.detail-item.spaced-content:has(div:contains(Khí áp))").first();

			// lấy dữ liệu ở div thứ 2 (giá trị)
			String pressure = pressureBlock.select("div:nth-of-type(2)").text();
			String cloudCover = doc.select("div:nth-of-type(9) div:nth-of-type(2)").text();
			
			String visibilityText = doc.select("div:nth-of-type(10) div:nth-of-type(2)").text();
			double visibilityValue = Double.parseDouble(visibilityText.split(" ")[0]);
			double visibilityKm = visibilityValue * 1.60934;
			String visibility = String.format("%.1f km", visibilityKm);

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

