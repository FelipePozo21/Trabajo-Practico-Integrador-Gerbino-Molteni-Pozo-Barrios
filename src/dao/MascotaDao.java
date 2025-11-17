/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exception.DaoException;
import entities.Mascota;
import entities.Microchip;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * DAO para operaciones CRUD de Mascota.
 */
public class MascotaDao implements GenericDao<Mascota> {

  // Dependencia hacia otro DAO.
  // Sirve para poder cargar el Microchip asociado a la Mascota cuando se necesite.
  private final MicrochipDao microchipDao;

  public MascotaDao() {
    this.microchipDao = new MicrochipDao();
  };
  
  /**
   * Crea un microchip en la DB.
   * 
   * @param mascota Mascota a crear (Sin id asignado).
   * @param conexion conexion activa a la db
   * @throws DaoException si ocurre un error SQL o si no se inserta ningun registro.
   */
  
  @Override
  public void crear(Mascota mascota, Connection conexion) throws DaoException {
    String sql = "INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio) "
            + "Values (?, ?, ?, ?, ? , ?)";
    //Try with resources (resources a declarar)
    try (PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setBoolean(1, mascota.isEliminado());
      statement.setString(2, mascota.getNombre());
      statement.setString(3, mascota.getEspecie());
      statement.setString(4, mascota.getRaza());
      statement.setDate(5, mascota.getFechaNacimiento() != null
              ? Date.valueOf(mascota.getFechaNacimiento()) : null);
      statement.setString(6, mascota.getDuenio());

      int filasAfectadas = statement.executeUpdate();

      if (filasAfectadas == 0) {
        throw new DaoException("No se pudo crear la mascota");
      }

      // Recuperar el id generado en la base y lo asignamos al objeto
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          mascota.setId(generatedKeys.getLong(1));
        }
      }

    } catch (SQLException e) {
      throw new DaoException("Error al crear mascota" + e.getMessage(), e);
    }
  }

  @Override
  public Mascota leer(Long id, Connection conexion) throws DaoException {
    String sql = "SELECT * FROM mascota WHERE id = ? AND eliminado = FALSE";
    
    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if(resultSet.next()) {
          return mapearMascota(resultSet, conexion);
        }
        return null;
      }
    } catch (SQLException e) {
      throw new DaoException("Error al leer mascota: " + e.getMessage(), e);
    }
  }

  @Override
  public List<Mascota> leerTodos(Connection conexion) throws DaoException {
    String sql = "SELECT * FROM mascota WHERE eliminado = FALSE ORDER BY id";
    List<Mascota> mascotas = new ArrayList<>();

    try (PreparedStatement statement = conexion.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        mascotas.add(mapearMascota(resultSet, conexion));
      }
      
      return mascotas;
      
    } catch (SQLException e) {
      throw new DaoException("Error al leer todas las mas mascotas: " + e.getMessage(), e);
    }
  }
 

  @Override
  public void actualizar(Mascota mascota, Connection conexion) throws DaoException {
    String sql = "UPDATE mascota SET nombre = ?, especie = ?, raza = ?, " + "fecha_nacimiento = ?, duenio = ? WHERE id = ?";
    
    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
      statement.setString(1, mascota.getNombre());
      statement.setString(2, mascota.getEspecie());
      statement.setString(3, mascota.getRaza());
      statement.setDate(4, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
      statement.setString(5, mascota.getDuenio());
      statement.setLong(6, mascota.getId());
      
      int filasAfectadas = statement.executeUpdate();
      
      if( filasAfectadas == 0 ) {
        throw new DaoException("No se encontre la mascota con id: " + mascota.getId());
      }
      
    } catch (SQLException e) {
      throw new DaoException("Error al actualizar la mascota" + e.getMessage(), e);
    }
  }
  
  @Override
  public void eliminar(Long id, Connection conexion) throws DaoException {
    String sql = "UPDATE mascota SET eliminado = TRUE WHERE id = ?";
    
    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
     statement.setLong(1, id);
     
     int filasAfectadas = statement.executeUpdate();
     
     if (filasAfectadas == 0) {
       throw new DaoException("No se encontro la mascota con id: " + id);
     }
    } catch (SQLException e) {
      throw new DaoException("Error al eliminar la mascota" + e.getMessage(), e);
    }
  }

    public void asociarMicrochip(Long microchipId, Long mascotaId, Connection conn) throws DaoException {
        String sql = "UPDATE mascota SET microchip_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, microchipId);
            stmt.setLong(2, mascotaId);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new DaoException("No se pudo asociar el microchip a la mascota");
            }
            
        } catch (SQLException e) {
            throw new DaoException("Error al asociar microchip: " + e.getMessage(), e);
        }
    }
    
  public Mascota buscarMascotaPorMicrochipId(Long microchipId, Connection conn) throws DaoException {
      String sql = "SELECT * FROM mascota WHERE microchip_id = ? AND eliminado = FALSE";

      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
          stmt.setLong(1, microchipId);

          try (ResultSet rs = stmt.executeQuery()) {
              if (rs.next()) {
                  return mapearMascota(rs, conn);
              }
              return null;
          }
      } catch (SQLException e) {
          throw new DaoException("Error al buscar mascota por microchip: " + e.getMessage(), e);
      }
  }
  
 /**
  * Convierte un registro del ResultSet en un objeto Mascota completo.
  * Tambien carga el microchip relacionado (relacion 1:1).
  *
  * @param rs cursor ya posicionado en un registro de la tabla mascota.
  * @param conn conexión activa para buscar el microchip asociado.
  * @return un objeto Mascota completamente reconstruido.
  */
  private Mascota mapearMascota(ResultSet ResultSet, Connection conexion) throws SQLException, DaoException {
    Mascota mascota = new Mascota();
    
    mascota.setId(ResultSet.getLong("id"));
    mascota.setEliminado(ResultSet.getBoolean("eliminado"));
    mascota.setNombre(ResultSet.getString("nombre"));
    mascota.setEspecie(ResultSet.getString("especie"));
    mascota.setRaza(ResultSet.getString("raza"));
    
    Date fechaNac = ResultSet.getDate("fecha_nacimiento");
    if (fechaNac != null) {
      mascota.setFechaNacimiento(fechaNac.toLocalDate());
    }
    
    mascota.setDuenio(ResultSet.getString("duenio"));
    
    // Cargar Microchip asociado (si existe).
    Microchip microchip = microchipDao.buscarPorMascotaId(mascota.getId(), conexion); // TODO completar metodo
    if(microchip != null) {
      mascota.setMicrochip(microchip);
    }
    
    return mascota;
  }
}


