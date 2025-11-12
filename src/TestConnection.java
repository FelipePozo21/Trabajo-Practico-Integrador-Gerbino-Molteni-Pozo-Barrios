

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DatabaseConnection;
import java.util.Date;

public class TestConnection {
    public static void main(String[] args) {
        // Try que inicia la conexion con la Base de Datos llamando al metodo getConexion
        try (Connection conexion = DatabaseConnection.getConexion()) {
            
            // Condicional con instrucciones cuando conecte bien
            if (conexion != null) {
                System.out.println("Conexion con la BD establecida.");
                
                // Llamado por SQL para traer la tabla mascota y mostrar informacion solo para verificar la conexion
                String sql = "SELECT * FROM mascota";
                try (PreparedStatement pstmt = conexion.prepareStatement(sql);
                      ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("\nLista de mascotas: ");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        String especie = rs.getString("especie");
                        String raza = rs.getString("raza");
                        Date nacimiento = rs.getDate("fecha_nacimiento");
                        String duenio = rs.getString("duenio");
                        System.out.println(
                                "Id: " + id + 
                                ". Nombre: " + nombre + 
                                ". Especie: " + especie +
                                ". Raza: " + raza +
                                ". Fecha de Nacimiento: " + nacimiento +
                                ". Duenio: " + duenio + ".\n");
                    }
                }
            } else {
                System.out.println("No se pudo establecer la conexion.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la BD: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
       
}
