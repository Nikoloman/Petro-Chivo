package com.example.chivo;

public class DBdata2 {
    private String Sucursal, Proveedor, Fecha, Hora,Litros;

    public DBdata2(){

    }

    public DBdata2(String sucursal, String proveedor, String fecha, String hora, String litros){
        this.Sucursal = Sucursal;
        this.Proveedor = Proveedor;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Litros = Litros;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String Sucursal) {
        this.Sucursal = Sucursal;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        Proveedor = proveedor;
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

    public String getLitros() {
        return Litros;
    }

    public void setLitros(String Litros) {
        this.Litros = Litros;
    }

}
