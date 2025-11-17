package dao;

import exception.DaoException;
import model.Microchip;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones CRUD de Microchip.
 */
public class MicrochipDao implements GenericDao<Microchip> {
    
    @Override
    public void crear(Microchip microchip, Connection conn) throws DaoException {
        String sql = "INSERT INTO microchip (eliminado, codigo, fecha_implantacion, " +
                     "veterinaria, observaciones) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setBoolean(1, microchip.isEliminado());
            stmt.setString(2, microchip.getCodigo());
            stmt.setDate(3, microchip.getFechaImplementacion() != null ? 
                         Date.valueOf(microchip.getFechaImplementacion()) : null);
            stmt.setString(4, microchip.getVeterinaria());
            stmt.setString(5, microchip.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new DaoException("No se pudo crear el microchip");
            }
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                  microchip.setId(generatedKeys.getLong(1));
                }
            }
            
        } catch (SQLException e) {
            throw new DaoException("Error al crear microchip: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Microchip leer(Long id, Connection conn) throws DaoException {
        String sql = "SELECT * FROM microchip WHERE id = ? AND eliminado = FALSE";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMicrochip(rs);
                }
                return null;
            }
            
        } catch (SQLException e) {
            throw new DaoException("Error al leer microchip: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Microchip> leerTodos(Connection conn) throws DaoException {
        String sql = "SELECT * FROM microchip WHERE eliminado = FALSE ORDER BY id";
        List<Microchip> microchips = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                microchips.add(mapearMicrochip(rs));
            }
            
            return microchips;
            
        } catch (SQLException e) {
            throw new DaoException("Error al leer todos los microchips: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void actualizar(Microchip microchip, Connection conn) throws DaoException {
        String sql = "UPDATE microchip SET codigo = ?, fecha_implantacion = ?, " +
                     "veterinaria = ?, observaciones = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, microchip.getCodigo());
            stmt.setDate(2, microchip.getFechaImplementacion() != null ? 
                         Date.valueOf(microchip.getFechaImplementacion()) : null);
            stmt.setString(3, microchip.getVeterinaria());
            stmt.setString(4, microchip.getObservaciones());
            stmt.setLong(5, microchip.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new DaoException("No se encontró el microchip con id: " + microchip.getId());
            }
            
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar microchip: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void eliminar(Long id, Connection conn) throws DaoException {
        String sql = "UPDATE microchip SET eliminado = TRUE WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new DaoException("No se encontró el microchip con id: " + id);
            }
            
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar microchip: " + e.getMessage(), e);
        }
    }
    
    /**
     * Busca un microchip asociado a una mascota especifica.
     */
   public Microchip buscarPorMascotaId(Long mascotaId, Connection conn) throws DaoException {
        String sql = "SELECT * FROM microchip WHERE id = (SELECT microchip_id FROM mascota WHERE id = ?) AND eliminado = FALSE";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, mascotaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMicrochip(rs);
                }
                return null;
            }
            
        } catch (SQLException e) {
            throw new DaoException("Error al buscar microchip por mascota: " + e.getMessage(), e);
        }
    } 
    
    /**
     * Mapea un ResultSet a un objeto Microchip.
     */
    private Microchip mapearMicrochip(ResultSet rs) throws SQLException {
        Microchip microchip = new Microchip();
        microchip.setId(rs.getLong("id"));
        microchip.setEliminado(rs.getBoolean("eliminado"));
        microchip.setCodigo(rs.getString("codigo"));
        
        Date fechaImpl = rs.getDate("fecha_implantacion");
        if (fechaImpl != null) {
            microchip.setFechaImplementacion(fechaImpl.toLocalDate());
        }
        
        microchip.setVeterinaria(rs.getString("veterinaria"));
        microchip.setObservaciones(rs.getString("observaciones"));
        
        return microchip;
    }
}
