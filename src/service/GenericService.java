package service;

import config.DatabaseConnection;
import dao.GenericDao;
import exception.DaoException;
import exception.ServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase base para Services con gestión de transacciones.
 * @param <T> Tipo de entidad
 */
public abstract class GenericService<T> {
    
    protected final GenericDao<T> dao;
    
    public GenericService(GenericDao<T> dao) {
        this.dao = dao;
    }
    
    /**
     * Inserta una nueva entidad con transacción.
     */
    public void insertar(T entity) throws ServiceException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            conn.setAutoCommit(false);
            
            validarParaInsertar(entity);
            dao.crear(entity, conn);
            
            conn.commit();
            
        } catch (SQLException | DaoException e) {
            rollback(conn);
            throw new ServiceException("Error al insertar: " + e.getMessage(), e);
        } finally {
            cerrarConexion(conn);
        }
    }
    
    /**
     * Obtiene una entidad por ID.
     */
    public T getById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("ID invalido");
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            return dao.leer(id, conn);
            
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error al obtener por ID: " + e.getMessage(), e);
        } finally {
            cerrarConexion(conn);
        }
    }
    
    /**
     * Obtiene todas las entidades activas.
     */
    public List<T> getAll() throws ServiceException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            return dao.leerTodos(conn);
            
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error al obtener todas: " + e.getMessage(), e);
        } finally {
            cerrarConexion(conn);
        }
    }
    
    /**
     * Actualiza una entidad con transacción.
     */
    public void actualizar(T entity) throws ServiceException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            conn.setAutoCommit(false);
            
            validarParaActualizar(entity);
            dao.actualizar(entity, conn);
            
            conn.commit();
            
        } catch (SQLException | DaoException e) {
            rollback(conn);
            throw new ServiceException("Error al actualizar: " + e.getMessage(), e);
        } finally {
            cerrarConexion(conn);
        }
    }
    
    /**
     * Elimina lógicamente una entidad con transacción.
     */
    public void eliminar(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("ID invalido");
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            conn.setAutoCommit(false);
            
            dao.eliminar(id, conn);
            
            conn.commit();
            
        } catch (SQLException | DaoException e) {
            rollback(conn);
            throw new ServiceException("Error al eliminar: " + e.getMessage(), e);
        } finally {
            cerrarConexion(conn);
        }
    }
    
    /**
     * Validaciones antes de insertar.
     */
    protected abstract void validarParaInsertar(T entity) throws ServiceException;
    
    /**
     * Validaciones antes de actualizar.
     */
    protected abstract void validarParaActualizar(T entity) throws ServiceException;
    
    /**
     * Realiza rollback en caso de error.
     */
    protected void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                System.out.println("ROLLBACK APLICADO, la transaccion fue revertida correctamente.");
            } catch (SQLException e) {
                System.err.println("Error en rollback: " + e.getMessage());
            }
        }
    }
    
    /**
     * Cierra la conexión y restaura autoCommit.
     */
    protected void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
