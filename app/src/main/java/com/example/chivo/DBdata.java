package com.example.chivo;

public class DBdata {
    private String Id, Sucursal, Folio, Fecha, Hora, Ruta, Dueño, Placas, Unidad, Litros, Pagado, Proveedor;

    public DBdata(){

    }

    public DBdata(String id, String sucursal, String folio, String fecha, String hora, String ruta, String dueño
            ,String placas, String unidad, String litros, String pagado){
        this.Id = id;
        this.Sucursal = Sucursal;
        this.Folio = Folio;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Ruta = Ruta;
        this.Placas = Placas;
        this.Litros = Litros;
        this.Unidad = Unidad;
        this.Pagado = Pagado;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = Id;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String Sucursal) {
        this.Sucursal = Sucursal;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String Folio) {
        this.Folio = Folio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String Ruta) {
        this.Ruta = Ruta;
    }

    public String getDueño() {
        return Dueño;
    }

    public void setDueño(String Dueño) {
        this.Dueño = Dueño;
    }

    public String getPlacas() {
        return Placas;
    }

    public void setPlacas(String Placas) {
        this.Placas = Placas;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String Unidad) {
        this.Unidad = Unidad;
    }

    public String getLitros() {
        return Litros;
    }

    public void setLitros(String Litros) {
        this.Litros = Litros;
    }

    public String getPagado() {
        return Pagado;
    }

    public void setPagado(String Pagado) {
        this.Pagado = Pagado;
    }
}