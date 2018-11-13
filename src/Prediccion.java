import java.util.Date;

public class Prediccion {

    private String nombreCiudad, icono, codigoPais;
    private double tempActual, tempMin, tempMax;
    private long humedad;
    private Date fecha, amanecer, ocaso;

    public Prediccion(String nombreCiudad, String icono, String codigoPais, double tempActual, double tempMin, double tempMax, long humedad, Date fecha, Date amanecer, Date ocaso) {
        this.nombreCiudad = nombreCiudad;
        this.icono = icono;
        this.codigoPais = codigoPais;
        this.tempActual = tempActual;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humedad = humedad;
        this.fecha = fecha;
        this.amanecer = amanecer;
        this.ocaso = ocaso;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public String getIcono() {
        return icono;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
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

    public double getTempActual() {
        return tempActual;
    }

    public String getCodigoPais() {
        return codigoPais;
    }
}
