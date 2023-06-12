import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class AmazonScraper {
    public static void main(String[] args) {
        try {
            // Retry counter and maximum number of retries
            int retries = 0;
            int maxRetries = 3;

            while (retries < maxRetries) {
                try {
                    // Wait for a few seconds before making the request
                    Thread.sleep(5000);

                    // Send a GET request to Amazon search results page with User-Agent header
                    Document doc = Jsoup.connect("https://www.amazon.com/s?k=games")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36")
                            .get();

                    // Select all the product elements
                    Elements products = doc.select("div[data-asin]");

                    // Create a FileWriter to write the data to a CSV file
                    FileWriter writer = new FileWriter("amazon_games.csv");
                    writer.write("Title,Price (EUR)\n");

                    // Iterate over the product elements and extract title and price information
                    for (Element product : products) {
                        String title = product.select("span.a-text-normal").text();
                        String price = product.select("span.a-price-whole").text();

                        // Write the title and price to the CSV file
                        writer.write("\"" + title + "\",\"" + price + "\"\n");
                    }

                    // Close the FileWriter
                    writer.close();

                    System.out.println("Scraping completed. Check amazon_games.csv for the results.");
                    break; // Break the retry loop if the scraping is successful

                } catch (IOException e) {
                    e.printStackTrace();
                    retries++;
                }
            }

            if (retries >= maxRetries) {
                System.out.println("Failed to scrape the data. Maximum retries reached.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
