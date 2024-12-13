package modelo.consumosAPIs.servicioGeoLocalizacion;

import modelo.elementos.Areas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalizadorLatLong {

    public static LatLong obtenerLatitudYLongitud(String direccion) {
        List<LatLong> coordenadas = generarCoordenadasCABA(10);
        Random random = new Random();
        int randomIndex = random.nextInt(coordenadas.size());
        return coordenadas.get(randomIndex);
    }

    public static Areas obtenerArea(String direccion) {
        List<Areas> areas = List.of(Areas.values());
        Random random = new Random();
        int randomIndex = random.nextInt(areas.size());
        return areas.get(randomIndex);
    }

    public static List<LatLong> generarCoordenadasCABA(int cantidad) {
        List<LatLong> coordenadas = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            double latitud = -34.705 + (random.nextDouble() * (34.545 - 34.705));
            double longitud = -58.505 + (random.nextDouble() * (58.345 - 58.505));
            coordenadas.add(new LatLong(latitud, longitud));
        }
        return coordenadas;
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