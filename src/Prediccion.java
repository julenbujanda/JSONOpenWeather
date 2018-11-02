import java.util.Date;

public class Prediccion {

    private String nombreCiudad;
    private int temp_min,temp_max,humedad;
    private Date amanecer,ocaso;

    public Prediccion(String nombreCiudad, int temp_min, int temp_max, int humedad, Date amanecer, Date ocaso) {
        this.nombreCiudad = nombreCiudad;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humedad = humedad;
        this.amanecer = amanecer;
        this.ocaso = ocaso;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public int getHumedad() {
        return humedad;
    }

    public Date getAmanecer() {
        return amanecer;
    }

    public Date getOcaso() {
        return ocaso;
    }
}
