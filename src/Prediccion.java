import java.util.Date;

public class Prediccion {

    private String nombreCiudad;
    private double temp_min, temp_max;
    private long humedad;
    private Date fecha, amanecer, ocaso;

    public Prediccion(String nombreCiudad, double temp_min, double temp_max, long humedad, Date fecha, Date amanecer, Date ocaso) {
        this.nombreCiudad = nombreCiudad;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humedad = humedad;
        this.fecha = fecha;
        this.amanecer = amanecer;
        this.ocaso = ocaso;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public long getHumedad() {
        return humedad;
    }

    public Date getAmanecer() {
        return amanecer;
    }

    public Date getOcaso() {
        return ocaso;
    }

    public Date getFecha() {
        return fecha;
    }
    
}
