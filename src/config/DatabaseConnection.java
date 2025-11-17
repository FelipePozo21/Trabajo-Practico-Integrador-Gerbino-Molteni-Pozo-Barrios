package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Ingresar cada uno su usuario, contraseña y direccion en donde tiene la base de datos
    private static final String url = "jdbc:mysql://127.0.0.1:3306/mascotas_db";
    private static final String user = "root";
    private static final String pass = "";
    
    // Metodo para el Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error: No se encontro el driver JDBC", e);
        }
    }
    
    // Metodo para conectarse con la Base de Datos
    public static Connection getConexion() throws SQLException {
        /*
        if (url == null || url.isEmpty() || user == null || user.isEmpty() || pass == null || pass.isEmpty()) {
            throw new SQLException("Configuracion de la base de datos invalida.");
        }
        */
        return DriverManager.getConnection(url, user, pass);
    }
}
