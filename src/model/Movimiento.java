package model;

public class Movimiento {
    private int id_movimiento;
    private int idUsuario;
    private String concepto;
    private double cantidad;
    private int id_categoria;

    public Movimiento(int idUsuario, String concepto, double cantidad, int categoria) {
        this.idUsuario = idUsuario;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.id_categoria = categoria;
    }

    public Movimiento(int id_movimiento, int idUsuario, String concepto, double cantidad, int categoria) {
        this.id_movimiento = id_movimiento;
        this.idUsuario = idUsuario;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.id_categoria = categoria;
    }

    public int getIdUsuario()
{
        return idUsuario;
    }
    public String getConcepto()
{
        return concepto;
    }
    public double getCantidad()
{
        return cantidad;
    }
    public int getId_categoria()
{
        return id_categoria;
    }
    public int getId()
{
        return id_movimiento;
    }

    @Override
    public String toString() {
        return  "\n---Movimiento---" +
                "\n- id=" + id_movimiento +
                ",\n- concepto='" + concepto + '\'' +
                ",\n- cantidad=" + cantidad + "€" +
                ",\n- categoria='" + id_categoria + '\'';
    }
}