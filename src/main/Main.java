package main;

import db.ConexionBD;
import db.ManageDB;
import java.sql.Connection;
import java.util.Scanner;
import model.Movimiento;
import model.Usuario;

public class Main 
{
    private static final Connection CONEXION = ConexionBD.conectar();
    private static final ManageDB MANAGER = new ManageDB(CONEXION);

    private static Usuario usuarioActual = null;
    private static boolean appEncendida = true;


    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) 
    {
        while (appEncendida) { // BUCLE 1: La aplicación principal
            
            if (usuarioActual == null) {
                // MENU DE BIENVENIDA (Login / Registro / Salir)
                login();
            } else {
                // MENU DE SESIÓN (Ver movimientos / Añadir / Logout)
                menuPrincipal();
            }
        }
        System.out.println("Gracias por usar EcoGest. ¡Hasta pronto!");
    }





    public static int solicitarInicioSesion()
    {
        int opcion = -1;

        System.out.println("""
                            1. Iniciar sesión
                            2. Registrar usuario
                            0. Salir
                          """);
        do 
        { 
            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(sc.nextLine());    
                if(opcion != 1 && opcion != 2 && opcion != 0)
                {
                    System.out.println("Opción inválida, inténtelo de nuevo");
                    opcion = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingrese un número");
                opcion = -1;
            }
        } while (opcion == -1);
        return opcion;
    }

    public static boolean login()
    {
        boolean sesionIniciada = false;
        Usuario userLogueado;
        imprimirBienvenida();
        
        while (!sesionIniciada && appEncendida) {
            switch (solicitarInicioSesion()) {
                case 1:
                    System.out.println("Inicio de sesión");
                    String nombre = solicitarNombre();
                    String password = solicitarContrasenya();
                    userLogueado = MANAGER.login(nombre, password);
                    if(userLogueado != null)
                    {
                        System.out.println("Sesión iniciada correctamente.");
                        usuarioActual = userLogueado;
                        sesionIniciada = true;
                    } else {
                        System.out.println("Credenciales incorrectas. Intente de nuevo.");
                    }
                    break;
                case 2:
                    System.out.println("Registrar usuario");
                    String nuevoNombre = solicitarNombre();
                    String nuevoPassword = solicitarContrasenya();
                    if(MANAGER.registrarUsuario(nuevoNombre, nuevoPassword))
                    {
                        System.out.println("Usuario registrado correctamente.");
                        // Hacer login automático después del registro
                        userLogueado = MANAGER.login(nuevoNombre, nuevoPassword);
                        if(userLogueado != null)
                        {
                            usuarioActual = userLogueado;
                            sesionIniciada = true;
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    appEncendida = false;
                    break;
                default:
                    throw new AssertionError();
            }
        }
        return sesionIniciada;
    }
    public static void imprimirBienvenida()
    {
        System.out.println("""
                ***************
                    ECOGEST
                ***************
                """);
        System.out.println("");
    }
    public static int solicitarSeleccionMovimientos()
    {
        int opcion = -1;
        System.out.println("""
                
                -------------------
                    MOVIMIENTOS
                -------------------
                1. Consultar saldo
                2. Registrar movimiento
                3. Consultar movimientos
                4. Eliminar movimiento
                0. Salir
                """);
        do 
        { 
            try 
            {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(sc.nextLine());    
                if(opcion != 1 && opcion != 2 && opcion != 3 && opcion != 4 && opcion != 0)
                {
                    System.out.println("Opción inválida, inténtelo de nuevo");
                    opcion = -1;
                }
            } catch(Exception e)
            {    
                System.out.println("Entrada inválida, ingrese un número");
                opcion = -1;
            }
        } while (opcion == -1);
        return opcion;
    }

    public static void menuPrincipal()
    {
        boolean sesionActiva = true;
        
        while (sesionActiva && usuarioActual != null) 
        {
            int opcion = solicitarSeleccionMovimientos();
            
            switch (opcion) 
            {
                case 1:
                    System.out.println("Saldo actual: " + MANAGER.obtenerSaldoTotal(usuarioActual.getId()));
                    break;
                case 2:
                    System.out.println((MANAGER.registrarMovimiento(solicitarMovimiento()) ? "Movimiento registrado correctamente" : "Error en el movimiento"));
                    break;
                case 3:
                    MANAGER.obtenerMovimientosPorUsuario(usuarioActual.getId());
                    break;
                case 4:
                    try 
                    {
                        MANAGER.eliminarMovimiento(solicitarIdMovimiento(), usuarioActual.getId());    
                    } catch (Exception d) 
                    {
                        System.out.println("Error al eliminar: " + d.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Cerrando sesión...");
                    usuarioActual = null;
                    sesionActiva = false;
                    break;
                default:
                    System.out.println("Opción desconocida");
            }
        }
    }
    public static String solicitarNombre()
    {
        System.out.print("Introduzca el nombre: ");
        return sc.nextLine();
    }
    public static String solicitarContrasenya()
    {
        System.out.print("Introduzca la contraseña: ");
        return sc.nextLine();
    }
    public static Movimiento solicitarMovimiento()
    {
        Movimiento m = new Movimiento(usuarioActual.getId(), solicitarConcepto(), solicitarCantidad(), solicitarCategoria());
        return m;
    }
    public static String solicitarConcepto()
    {
        System.out.print("Introduzca el concepto: ");
        return sc.nextLine();
    }
    public static double solicitarCantidad()
    {
        System.out.print("Introduzca la cantidad: ");
        return Double.parseDouble(sc.nextLine());
    }
    public static int solicitarCategoria()
    {
        System.out.print("Introduzca el id de la categoría: ");
        return Integer.parseInt(sc.nextLine());
    }
    public static int solicitarIdMovimiento()
    {
        System.out.print("Introduce el id del movimiento: ");
        return Integer.parseInt(sc.nextLine());
    }
}
