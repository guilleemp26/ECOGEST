package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import model.Movimiento;
import model.Usuario;



public class ManageDB {
    private Connection connection;

    // Tu constructor actual (está perfecto)
    public ManageDB(Connection connection) 
    {
        try {
            if (connection != null && connection.isValid(0)) {
                this.connection = connection;
            } else {
                System.out.println("Error, conexión inválida: ManageDB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODOS DE USUARIO ---

    // 1. Validador Regex (8 caracteres, 1 Mayúscula, 1 Número)
    public boolean validarPassword(String password) 
    {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    // 2. Método para Registrar
    public boolean registrarUsuario(String nombre, String password) 
    {
        if (!validarPassword(password)) {
            System.out.println("---Contraseña inválida---");
            System.out.println("- La contraseña debe tener:");
            System.out.println("- Al menos 8 caracteres");
            System.out.println("- Una letra mayúscula");
            System.out.println("- Una letra minúscula");
            System.out.println("- Al menos un número");
            return false;
        }

        String sql = "INSERT INTO usuarios (nombre, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, password);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    // 3. Método para Login (Devuelve el ID del usuario si es correcto, o -1 si falla)
    public Usuario login(String nombre, String password) 
    {
        // Buscamos el ID y el nombre (por si acaso)
        String sql = "SELECT id_usuario, nombre FROM usuarios WHERE nombre = ? AND password = ?";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // SI EXISTE: Creamos el objeto y lo devolvemos
                int id = rs.getInt("id_usuario");
                String nombreDb = rs.getString("nombre");
                
                return new Usuario(id, nombreDb, password); 
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        // SI NO EXISTE O HAY ERROR: Devolvemos null
        return null; 
    }

    public boolean registrarMovimiento(Movimiento m) 
    {
        // SQL sin columna 'fecha', porque Supabase usará el valor por defecto (CURRENT_DATE)
        String sql = "INSERT INTO movimientos (id_usuario, concepto, cantidad, id_categoria) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, m.getIdUsuario());
            pstmt.setString(2, m.getConcepto());
            pstmt.setDouble(3, m.getCantidad());
            pstmt.setInt(4, m.getId_categoria());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar movimiento: " + e.getMessage());
            return false;
        }
    }

    //Listar movimientos
    public java.util.List<Movimiento> obtenerMovimientosPorUsuario(int idUsuario) 
    {
        java.util.List<Movimiento> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM movimientos WHERE id_usuario = ? ORDER BY id_movimiento DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Creamos el objeto con el constructor de 5 parámetros que hicimos antes
                Movimiento m = new Movimiento(
                    rs.getInt("id_movimiento"),
                    rs.getInt("id_usuario"),
                    rs.getString("concepto"),
                    rs.getDouble("cantidad"),
                    rs.getInt("id_categoria")
                );
                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        for(Movimiento m : lista)
        {
            System.out.println(m);
        }
        return lista;
    }

    //Obtener valance total
    public double obtenerSaldoTotal(int idUsuario) 
    {
        String sql = "SELECT SUM(cantidad) as saldo FROM movimientos WHERE id_usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular saldo: " + e.getMessage());
        }
        return 0.0;
    }

    //Eliminar movimiento
    public boolean eliminarMovimiento(int idMovimiento, int idUsuario) {
        // Importante: Filtramos por idUsuario por seguridad, para que nadie borre gastos ajenos
        String sql = "DELETE FROM movimientos WHERE id_movimiento = ? AND id_usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idMovimiento);
            pstmt.setInt(2, idUsuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al borrar: " + e.getMessage());
            return false;
        }
    }
}
