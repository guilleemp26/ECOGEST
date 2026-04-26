package model;

import db.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private int id;
    private String nombre;
    private String password;

    public Usuario login(String nombre, String password)
{
        String sql = "SELECT id_usuario FROM usuarios WHERE nombre = ? AND password = ?";

        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                return new Usuario(id, nombre, password);
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null;
    }

    public Usuario(int id, String nombre, String password)
{
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public int getId()
{
        return id;
    }
    public String getNombre()
{
        return nombre;
    }
    public String getPassword()
{
        return password;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPassword(String password) { this.password = password; }
}