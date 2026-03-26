package model;

public class Usuario {
    // Atributos privados (Solo los que dijiste: id, nombre, password)
    private int id;
    private String nombre;
    private String password;

    // CONSTRUCTOR 1: Para cuando creas un usuario nuevo (Registro)
    // El ID no se pone porque lo genera Supabase automáticamente
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    // CONSTRUCTOR 2: Para cuando el usuario ya existe (Login)
    // Aquí sí incluimos el ID que nos devuelve la base de datos
    public Usuario(int id, String nombre, String password) {
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