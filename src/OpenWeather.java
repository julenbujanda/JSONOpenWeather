import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class OpenWeather {

    private final static double KELVIN = 273.15;

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
        String icono = (String) ((JSONObject) ((JSONArray) rootObject.get("weather")).get(0)).get("icon");
        double tempActual = (double) ((JSONObject) rootObject.get("main")).get("temp") - KELVIN;
        double temp_min = (double) ((JSONObject) rootObject.get("main")).get("temp_min") - KELVIN;
        double temp_max = (double) ((JSONObject) rootObject.get("main")).get("temp_max") - KELVIN;
        long humedad = (long) ((JSONObject) rootObject.get("main")).get("humidity");
        Date fecha = new Date((long) rootObject.get("dt") * 1000);
        Date amanecer = new Date((long) ((JSONObject) rootObject.get("sys")).get("sunrise") * 1000);
        Date ocaso = new Date((long) ((JSONObject) rootObject.get("sys")).get("sunset") * 1000);
        System.out.println((String) ((JSONObject) rootObject.get("sys")).get("country"));
        return new Prediccion(nombreCiudad, icono, tempActual, temp_min, temp_max, humedad, fecha, amanecer, ocaso);
    }

    private static String generarHTML(Prediccion prediccion) {
        String fechaFormateada = new SimpleDateFormat("dd/MM/YYYY").format(prediccion.getFecha());
        SimpleDateFormat formateadorHora = new SimpleDateFormat("HH:mm");
        // language=HTML
        String html = "<html\n><head>\n<meta charset='UTF-8'>\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css\">\n" +
                "</head>\n<body>\n" +
                "<div style=\"text-align: center;\"><h1>" + prediccion.getNombreCiudad() + "</h1>\n" +
                "<h2><img src=\"http://openweathermap.org/img/w/" + prediccion.getIcono() + ".png\" style='height: 100px; width: auto;'></h2>\n" +
                "<h2>Temperatura actual: " + String.format("%.1f",prediccion.getTempActual()) + "</h2>" +
                "<h2>" + fechaFormateada + "</h2></div>\n" +
                "<table class='centered' style='margin: auto; width: 800px'>\n<tbody>\n" +
                "<tr><td>Amanecer</td><td>Ocaso</td><td>T. Máxima</td><td>T. Mínima</td></tr>\n" +
                "<tr><td>" + formateadorHora.format(prediccion.getAmanecer()) + "</td>" +
                "<td>" + formateadorHora.format(prediccion.getOcaso()) + "</td>" +
                "<td>" + String.format("%.1f",prediccion.getTempMax()) + "</td><td>" + String.format("%.1f",prediccion.getTempMin()) + "</td></tr>\n";
        // language=HTML
        html += "\n</tbody></table>\n</body>\n</html>\n";
        return html;
    }

    private static void generarFichero(String htmlPagina) {
        try {
            FileWriter fw = new FileWriter("./prediccion.html", false);
            fw.write(htmlPagina);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        generarFichero(generarHTML(parsearJSON(descargarJSON(scanner.nextLine()))));
    }

}
