package model;

public class Movimiento {
    private int id;
    private int idUsuario;
    private String concepto;
    private double cantidad;
    private String categoria;

    // CONSTRUCTOR: Para crear nuevos movimientos desde la App
    public Movimiento(int idUsuario, String concepto, double cantidad, String categoria) {
        this.idUsuario = idUsuario;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // CONSTRUCTOR: Para cuando listamos datos (por si necesitas el ID que dio la BD)
    public Movimiento(int id, int idUsuario, String concepto, double cantidad, String categoria) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // GETTERS: Imprescindibles para que ManageDB lea los datos
    public int getIdUsuario() 
    { 
        return idUsuario; 
    }
    public String getConcepto() 
    { return concepto; }
    public double getCantidad() { 
        return cantidad; 
    }
    public String getCategoria() 
    { 
        return categoria; 
    }
    public int getId() 
    { 
        return id; 
    }
}