import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class OpenWeather {

    private static String parsearJSON(String ciudad) throws IOException {
        String respuesta = "";
        URLConnection connection = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + ciudad + "&appid=57703a7a9ab7b873a99116a3ea379748").openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String linea;
        while ((linea = bufferedReader.readLine()) != null) {
            respuesta += linea;
        }
        return respuesta;
    }

    private static String generarHTML() {
        // language=HTML
        String html = "<html\n><head>\n<meta charset='UTF-8'>\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css\">\n" +
                "</head>\n<body>\n" +
                "<div style=\"text-align: center;\"><h1>" + localidad.getNombre() + "</h1>\n" +
                "<h2>" + localidad.getProvincia() + "</h2></div>\n" +
                "<table class='centered' style='margin: auto; width: 800px'>\n<tbody>\n" +
                "<tr><td>Sol</td><td>T. Máxima</td><td>T. Mínima</td></tr>\n";
        // language=HTML
        html += "\n</tbody></table>\n</body>\n</html>\n";
        return html;
    }

    public static void main(String[] args) {

    }

}
