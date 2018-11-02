import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Date;

public class OpenWeather {

    private static String descargarJSON(String ciudad) throws IOException {
        String respuesta = "";
        URLConnection connection = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + ciudad + "&appid=57703a7a9ab7b873a99116a3ea379748").openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String linea;
        while ((linea = bufferedReader.readLine()) != null) {
            respuesta += linea;
        }
        return respuesta;
    }

    private static Prediccion parsearJSON(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject rootObject = (JSONObject) parser.parse(json);
        String nombreCiudad = (String) rootObject.get("name");
        double temp_min = (double) ((JSONObject) rootObject.get("main")).get("temp_min") - 273;
        double temp_max = (double) ((JSONObject) rootObject.get("main")).get("temp_max") - 273;
        long humedad = (long) ((JSONObject) rootObject.get("main")).get("humidity");
        Date fecha = new Date((long) rootObject.get("dt")*1000);
        Date amanecer = new Date((long) ((JSONObject) rootObject.get("sys")).get("sunrise")*1000);
        Date ocaso = new Date((long) ((JSONObject) rootObject.get("sys")).get("sunset")*1000);
        return new Prediccion(nombreCiudad, temp_min, temp_max, humedad, fecha, amanecer, ocaso);
    }

    private static String generarHTML(Prediccion prediccion) {
        // language=HTML
        String html = "<html\n><head>\n<meta charset='UTF-8'>\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css\">\n" +
                "</head>\n<body>\n" +
                "<div style=\"text-align: center;\"><h1>" + prediccion.getNombreCiudad() + "</h1>\n" +
                "<h2>" + prediccion.getFecha() + "</h2></div>\n" +
                "<table class='centered' style='margin: auto; width: 800px'>\n<tbody>\n" +
                "<tr><td>Amanecer/Ocaso</td><td>T. Máxima</td><td>T. Mínima</td></tr>\n" +
                "<tr><td>" + prediccion.getAmanecer() + " " + prediccion.getOcaso() + "</td>" +
                "<td>" + prediccion.getTemp_max() + "</td><td>" + prediccion.getTemp_min() + "</td></tr>\n";
        // language=HTML
        html += "\n</tbody></table>\n</body>\n</html>\n";
        return html;
    }

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(parsearJSON(descargarJSON("Valencia")));
    }

}
