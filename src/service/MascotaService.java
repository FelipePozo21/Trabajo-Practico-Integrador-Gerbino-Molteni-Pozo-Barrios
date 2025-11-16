package service;

import config.DatabaseConnection;
import dao.MascotaDao;
import dao.MicrochipDao;
import exception.DaoException;
import exception.ServiceException;
import model.Mascota;
import model.Microchip;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service para operaciones de negocio de Mascota.
 */
public class MascotaService extends GenericService<Mascota> {
    
    private final MascotaDao mascotaDao;
    private final MicrochipDao microchipDao;
    
    public MascotaService() {
        super(new MascotaDao());
        this.mascotaDao = new MascotaDao();
        this.microchipDao = new MicrochipDao();
    }
    
    @Override
    protected void validarParaInsertar(Mascota mascota) throws ServiceException {
        if (mascota == null) {
            throw new ServiceException("La mascota no puede ser nula");
        }
        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            throw new ServiceException("El nombre es obligatorio");
        }
        if (mascota.getEspecie() == null || mascota.getEspecie().trim().isEmpty()) {
            throw new ServiceException("La especie es obligatoria");
        }
        if (mascota.getDuenio() == null || mascota.getDuenio().trim().isEmpty()) {
            throw new ServiceException("El dueño es obligatorio");
        }
    }
    
    @Override
    protected void validarParaActualizar(Mascota mascota) throws ServiceException {
        validarParaInsertar(mascota);
        
        if (mascota.getId() == null || mascota.getId() <= 0) {
            throw new ServiceException("ID inválido para actualizar");
        }
    }
    
    /**
     * Crea una mascota y le asigna un microchip en una sola transacción.
     * Garantiza la relación 1→1.
     */
    public void crearMascotaConMicrochip(Mascota mascota, Microchip microchip) throws ServiceException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            conn.setAutoCommit(false);
            
            // Validaciones
            validarParaInsertar(mascota);
            validarMicrochip(microchip);
            
            // 1. Crear microchip (BD genera su ID)
            microchipDao.crear(microchip, conn);
            
            // 2. Crear mascota (BD genera su ID)
            mascotaDao.crear(mascota, conn);
            
            // 3. Asociar microchip a mascota
            microchipDao.asociarMascota(microchip.getId(), mascota.getId(), conn);
            
            // 4. Actualizar objeto en memoria
            mascota.setMicrochip(microchip);
            
            conn.commit();
            
        } catch (SQLException | DaoException e) {
            rollback(conn);
            throw new ServiceException("Error al crear mascota con microchip: " + e.getMessage(), e);
        } finally {
            cerrarConexion(conn);
        }
    }
    
    /**
     * Asigna un microchip existente a una mascota.
     * Verifica que se mantenga la relación 1→1.
     */
    public void asignarMicrochip(Long mascotaId, Long microchipId) throws ServiceException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConexion();
            conn.setAutoCommit(false);
            
            // Verificar que la mascota existe
            Mascota mascota = mascotaDao.leer(mascotaId, conn);
            if (mascota == null) {
                throw new ServiceException("Mascota no encontrada con id: " + mascotaId);
            }
            
            // Verificar que la mascota no tenga ya un microchip
            if (mascota.getMicrochip() != null) {
                throw new ServiceException("La mascota ya tiene un microchip asignado");
            }
            
            // Verificar que el microchip existe
            Microchip microchip = microchipDao.leer(microchipId, conn);
            if (microchip == null) {
                throw new ServiceException("Microchip no encontrado con id: " + microchipId);
            }
            
            // Verificar que el microchip no esté asignado a otra mascota
            // (buscar si alguna mascota ya tiene ese microchip)
            Microchip microchipEnUso = microchipDao.buscarPorMascotaId(mascotaId, conn);
            if (microchipEnUso != null && !microchipEnUso.getId().equals(microchipId)) {
                throw new ServiceException("El microchip ya está asignado a otra mascota");
            }
            
            // Asignar
            microchipDao.asociarMascota(microchipId, mascotaId, conn);
            
            conn.commit();
            
        } catch (SQLException | DaoException e) {
            rollback(conn);
            throw new ServiceException("Error al asignar microchip: " + e.getMessage(), e);
        } finally {
            cerrarConexion(conn);
        }
    }
    
    /**
     * Valida un microchip.
     */
    private void validarMicrochip(Microchip microchip) throws ServiceException {
        if (microchip == null) {
            throw new ServiceException("El microchip no puede ser nulo");
        }
        if (microchip.getCodigo() == null || microchip.getCodigo().trim().isEmpty()) {
            throw new ServiceException("El código del microchip es obligatorio");
        }
    }
}