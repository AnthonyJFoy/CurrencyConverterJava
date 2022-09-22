import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Scanner; // The Scanner class is used to get user input
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException, InterruptedException {

        // create HashMap to hold the currencies
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

        // add various currency codes to map
        currencyCodes.put(1, "GBP");
        currencyCodes.put(2, "EUR");
        currencyCodes.put(3, "USD");
        currencyCodes.put(4, "AUD");
        currencyCodes.put(5, "CAD");
        currencyCodes.put(6, "JPY");
        currencyCodes.put(7, "HKD");
        currencyCodes.put(8, "NZD");

        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Currency converter application!");
        System.out.println("Which currency are you converting from?");
        System.out.println("1: GBP \t 2: EUR \t 3: USD \t 4: AUD \t 5: CAD \t 6: JPY \t 7: HKD \t 8: NZD"); // \t creates a tab between.
        fromCode = currencyCodes.get(sc.nextInt()); // take the int input from the user, choose correct currency from map and assign this to fromCode variable.

        System.out.println("Which currency are you converting to?");
        System.out.println("1: GBP \t 2: EUR \t 3: USD \t 4: AUD \t 5: CAD \t 6: JPY \t 7: HKD \t 8: NZD"); // \t creates a tab between.
        toCode = currencyCodes.get(sc.nextInt()); // take the int input from the user, choose correct currency from map and assign this to toCode variable.

        System.out.println("How much would you like to convert?");
        amount = sc.nextFloat(); // take the decimal input from user and assign to amount variable.

        sendHttpGETRequest(fromCode, toCode, amount);

        System.out.println("Conversion complete!");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder() // use api-ninjas from RapidAPI to obtain exchange rates.
                .uri(URI.create("https://currency-converter-by-api-ninjas.p.rapidapi.com/v1/convertcurrency?have=" + fromCode + "&want=" + toCode + "&amount=" + amount))
                .header("X-RapidAPI-Key", "134b1fd11emsh029e6dfc553a918p14e249jsn31d52d8499a1")
                .header("X-RapidAPI-Host", "currency-converter-by-api-ninjas.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        String toFloat = response.body().substring(15,21); // takes the exchanged float value from the response.body() string.
        float value = Float.parseFloat(toFloat);
        System.out.println(amount + " " + fromCode + " exchanges to " + value + " " + toCode);

    }
}
