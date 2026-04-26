package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:postgresql://aws-1-eu-west-1.pooler.supabase.com:6543/postgres?sslmode=require";
    private static final String USER = "postgres.mnodyncfuwvblaxwgqzb";
    private static final String PASS = "79/GM:458111";

    public static Connection conectar()
{
    Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");

            conexion = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("El Driver JAR no está siendo detectado por Java.");
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
    return conexion;
}
}
