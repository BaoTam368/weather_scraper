package weather_scraper;
import java.io.*;
import java.time.LocalDateTime;

import org.jsoup.*;
import org.jsoup.nodes.Document;

public class WeatherScraper {
	public static void main(String[] args) {
		try {
			String url = "https://www.accuweather.com/vi/vn/ho-chi-minh-city/353981/current-weather/353981";
			Document doc = Jsoup.connect(url).get();

			// Thứ, ngày tháng
			String dayDate = doc.select(".subnav-pagination div").text();
			String Phenomena = doc.select("div.current-weather div.phrase").text();

			// Các thông số khác (Accuweather thường đặt trong class riêng, bạn cần test
			// trực tiếp HTML)
			String temperature = doc.select("div.display-temp").text();
			String uvIndex = doc.select("div.detail-item:nth-of-type(3) div:nth-of-type(2)").text();
			String wind = doc.select("div.detail-item:nth-of-type(4) div:nth-of-type(2)").text();
			String humidity = doc.select("div.detail-item:nth-of-type(6) div:nth-of-type(2)").text();
			String dewPoint = doc.select("div.detail-item:nth-of-type(7) div:nth-of-type(2)").text();
			String pressure = doc.select("div:nth-of-type(8) div:nth-of-type(2)").text();
			String cloudCover = doc.select("div:nth-of-type(9) div:nth-of-type(2)").text();
			String visibility = doc.select("div:nth-of-type(10) div:nth-of-type(2)").text();
			String ceiling = doc.select("div:nth-of-type(11) div:nth-of-type(2)").text();

			System.out.println("Ngày giờ: " + dayDate);
			System.out.println("UV Index: " + uvIndex);
			System.out.println("Gió: " + wind);
			System.out.println("Độ ẩm: " + humidity);
			System.out.println("Điểm sương: " + dewPoint);
			System.out.println("Khí áp: " + pressure);
			System.out.println("Mật độ mây: " + cloudCover);
			System.out.println("Tầm nhìn: " + visibility);
			System.out.println("Trần mây: " + ceiling);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

