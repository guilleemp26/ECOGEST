package model;

import db.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    // Atributos privados (Solo los que dijiste: id, nombre, password)
    private int id;
    private String nombre;
    private String password;

    // CONSTRUCTOR 1: Para cuando creas un usuario nuevo (Registro)
    // El ID no se pone porque lo genera Supabase automáticamente
    public Usuario login(String nombre, String password) 
    {
        // Usamos 'id_usuario' y 'username' como en tu DB
        String sql = "SELECT id_usuario FROM usuarios WHERE nombre = ? AND password = ?";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Si hay éxito, creamos el objeto Usuario con su ID real de la base de datos
                int id = rs.getInt("id_usuario");
                return new Usuario(id, nombre, password);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en login: " + e.getMessage());
        }
        return null; // Si no hay coincidencia o hay error
    }

    // CONSTRUCTOR 2: Para cuando el usuario ya existe (Login)
    // Aquí sí incluimos el ID que nos devuelve la base de datos
    public Usuario(int id, String nombre, String password) 
    {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    // GETTERS: Para que otras clases puedan leer los datos
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

    // SETTERS: Por si necesitas cambiar algo en tiempo de ejecución
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPassword(String password) { this.password = password; }
}