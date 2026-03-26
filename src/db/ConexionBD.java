import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // 1. Datos de configuración (Sácalos de Supabase -> Settings -> Database)
    // El host suele ser algo como: db.xxxxxx.supabase.co
    private static final String HOST = "db.mnodyncfuwvblaxwgqzb.supabase.co"; 
    private static final String PORT = "5432";
    private static final String DB_NAME = "postgres"; // Por defecto en Supabase
    private static final String USER = "postgres";
    private static final String PASS = "79/GM:458111";

    // Construimos la URL JDBC para PostgreSQL
    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;

    // 2. El método que llamaremos desde los DAOs
    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Registramos el driver de PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Intentamos abrir la conexión
            conexion = DriverManager.getConnection(URL, USER, PASS);
            
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se encontró el Driver JDBC de PostgreSQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ERROR: No se pudo conectar a la base de datos.");
            System.err.println("Mensaje: " + e.getMessage());
        }
        return conexion;
    }
}