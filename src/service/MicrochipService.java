package service;

import config.DatabaseConnection;
import dao.MicrochipDao;
import exception.DaoException;
import exception.ServiceException;
import entities.Microchip;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service para operaciones de negocio de Microchip.
 */
public class MicrochipService extends GenericService<Microchip> {
    
    private final MicrochipDao microchipDao;
  
    public MicrochipService() {
        super(new MicrochipDao());
        this.microchipDao = (MicrochipDao) dao;
    }
    
    @Override
    protected void validarParaInsertar(Microchip microchip) throws ServiceException {
        if (microchip == null) {
            throw new ServiceException("El microchip no puede ser nulo");
        }
        if (microchip.getCodigo() == null || microchip.getCodigo().trim().isEmpty()) {
            throw new ServiceException("El codigo es obligatorio");
        }
        if (microchip.getCodigo().length() > 25) {
            throw new ServiceException("El codigo no puede exceder 25 caracteres");
        }
    }
    
    @Override
    protected void validarParaActualizar(Microchip microchip) throws ServiceException {
        validarParaInsertar(microchip);
        
        if (microchip.getId() == null || microchip.getId() <= 0) {
            throw new ServiceException("ID invalido para actualizar");
        }
    }
    
    public Microchip getByCodigo(String codigo) throws ServiceException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ServiceException("El codigo de busqueda no puede ser vacio.");
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            // Llama al nuevo metodo implementado en el DAO
            return microchipDao.buscarPorCodigo(codigo, conn);  
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error al buscar microchip por codigo: " + e.getMessage(), e);
        } finally {
            // Reutilizamos el metodo de GenericService para cerrar la conexion
            cerrarConexion(conn); 
        }
    }
    
}