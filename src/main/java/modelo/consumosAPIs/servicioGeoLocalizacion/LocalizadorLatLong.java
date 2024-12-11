package modelo.consumosAPIs.servicioGeoLocalizacion;

import modelo.elementos.Areas;

public class LocalizadorLatLong {

    public static Double obtenerLatitud(String direccion) {
        //TODO
        return 0.0;
    }

    public static Double obtenerLongitud(String direccion) {
        //TODO
        return 0.0;
    }

    public static Areas obtenerArea(Double latitud, Double longitud) {
        //TODO
        return Areas.BELGRANO;
    }
}
// TODO: consumir una API que nos devuelva esos valores!
/*
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeocodingExample {

    private static final String API_KEY = "YOUR_API_KEY"; //

    public static void main(String[] args) {
        String address = "1600 Amphitheatre Parkway, Mountain View, CA";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address.replace(" ", "+") + "&key=" + API_KEY;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray("results");
            if (results.length() > 0) {
                JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                System.out.println("Latitude: " + lat + ", Longitude: " + lng);
            } else {
                System.out.println("No results found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/