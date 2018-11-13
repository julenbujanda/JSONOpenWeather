import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class OpenWeather {

    private final static double KELVIN = 273.15;
    private static Scanner scanner;

    private static String descargarJSON() throws IOException {
        String respuesta = "";
        URLConnection connection = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + seleccionarCiudad() + "&appid=57703a7a9ab7b873a99116a3ea379748").openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String linea;
        while ((linea = bufferedReader.readLine()) != null) {
            respuesta += linea;
        }
        return respuesta;
    }

    private static String nombrePais(String codigoPais) {
        String json = "";
        try {
            URLConnection connection = new URL("https://restcountries.eu/rest/v2/alpha/" + codigoPais + "?fields=translations;").openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                json += linea;
            }
            JSONParser parser = new JSONParser();
            return (String) ((JSONObject) ((JSONObject) parser.parse(json)).get("translations")).get("es");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String seleccionarCiudad() {
        System.out.println("Seleccione ciudad:\n" +
                "1- Madrid\n" +
                "2- Villaviciosa de Odón\n" +
                "3- Vitoria-Gasteiz\n" +
                "4- Donostia-San Sebastián\n" +
                "5- Seleccionar otra ciudad");
        switch (scanner.nextInt()) {
            case 1:
                return "Madrid";
            case 2:
                return "Villaviciosa de Odón";
            case 3:
                return "Vitoria-Gasteiz";
            case 4:
                return "Donostia-San Sebastián";
            case 5:
                scanner.nextLine();
                System.out.println("Indique el nombre de la ciudad: ");
                return scanner.nextLine();
        }
        return null;
    }

    private static Prediccion parsearJSON(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject rootObject = (JSONObject) parser.parse(json);
        String nombreCiudad = (String) rootObject.get("name");
        String icono = (String) ((JSONObject) ((JSONArray) rootObject.get("weather")).get(0)).get("icon");
        String codigoPais = (String) ((JSONObject) rootObject.get("sys")).get("country");
        double tempActual = (double) ((JSONObject) rootObject.get("main")).get("temp") - KELVIN;
        double temp_min = (double) ((JSONObject) rootObject.get("main")).get("temp_min") - KELVIN;
        double temp_max = (double) ((JSONObject) rootObject.get("main")).get("temp_max") - KELVIN;
        long humedad = (long) ((JSONObject) rootObject.get("main")).get("humidity");
        Date fecha = new Date((long) rootObject.get("dt") * 1000);
        Date amanecer = new Date((long) ((JSONObject) rootObject.get("sys")).get("sunrise") * 1000);
        Date ocaso = new Date((long) ((JSONObject) rootObject.get("sys")).get("sunset") * 1000);
        return new Prediccion(nombreCiudad, icono, codigoPais, tempActual, temp_min, temp_max, humedad, fecha, amanecer, ocaso);
    }

    private static String generarHTML(Prediccion prediccion) {
        String fechaFormateada = new SimpleDateFormat("dd/MM/YYYY").format(prediccion.getFecha());
        SimpleDateFormat formateadorHora = new SimpleDateFormat("HH:mm");
        // language=HTML
        String html = "<html\n><head>\n<meta charset='UTF-8'>\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css\">\n" +
                "</head>\n<body>\n" +
                "<div style=\"text-align: center;\"><h1>" + prediccion.getNombreCiudad() + "</h1>\n" +
                "<h3>" + nombrePais(prediccion.getCodigoPais()) + "</h3>" +
                "<h2><img src=\"http://openweathermap.org/img/w/" + prediccion.getIcono() + ".png\" style='height: 100px; width: auto;'></h2>\n" +
                "<h4>Temperatura actual: " + String.format("%.1f", prediccion.getTempActual()) + "</h4>" +
                "<h5>" + fechaFormateada + "</h5></div>\n" +
                "<table class='centered' style='margin: auto; width: 800px'>\n<tbody>\n" +
                "<tr><td>Amanecer</td><td>Ocaso</td><td>T. Máxima</td><td>T. Mínima</td></tr>\n" +
                "<tr><td>" + formateadorHora.format(prediccion.getAmanecer()) + "</td>" +
                "<td>" + formateadorHora.format(prediccion.getOcaso()) + "</td>" +
                "<td>" + String.format("%.1f", prediccion.getTempMax()) + "</td><td>" + String.format("%.1f", prediccion.getTempMin()) + "</td></tr>\n";
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
        scanner = new Scanner(System.in);
        try {
            generarFichero(generarHTML(parsearJSON(descargarJSON())));
            Desktop.getDesktop().browse(new File("prediccion.html").toURI());
        } catch (FileNotFoundException e) {
            System.out.println("Ciudad no encontrada.");
        }
    }

}
