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
            throw new ServiceException("ID invalido para actualizar");
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
            mascotaDao.asociarMicrochip(microchip.getId(), mascota.getId(), conn);
            
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
     * le asigna un microchip a una mascota que exista (transaccion)
     * garantiza la relacion 1→1.
     */
    public void asignarMicrochip(Long mascotaId, Long microchipId) throws ServiceException {
        Connection conn = null;
        try {
            // verificar que los IDs no sean null
            if (mascotaId == null || microchipId == null) {
                throw new ServiceException("Error: Los IDs de mascota y microchip no pueden ser null. Verifique que se crearon correctamente.");
            }

            conn = DatabaseConnection.getConexion();
            conn.setAutoCommit(false);

            // verificar que la mascota existe
            Mascota mascota = mascotaDao.leer(mascotaId, conn);
            if (mascota == null) {
                throw new ServiceException("Error: Mascota con ID " + mascotaId + " no existe o fue eliminada.");
            }

            // verificar que el microchip existe
            Microchip microchip = microchipDao.leer(microchipId, conn);
            if (microchip == null) {
                throw new ServiceException("Error: Microchip con ID " + microchipId + " no existe o fue eliminado.");
            }

            // verificar que la mascota no tenga ya un microchip asignado
            Microchip microchipActual = microchipDao.buscarPorMascotaId(mascotaId, conn);
            if (microchipActual != null) {
                throw new ServiceException("Error: La mascota ya tiene un microchip asignado (ID: " + microchipActual.getId() + ").");
            }

            // verificar que el microchip no este asignado a otra mascota
            Mascota mascotaDelMicrochip = mascotaDao.buscarMascotaPorMicrochipId(microchipId, conn);
            if (mascotaDelMicrochip != null) {
                throw new ServiceException("Error: El microchip ya esta asignado a otra mascota (ID: " + mascotaDelMicrochip.getId() + ").");
            }


            mascotaDao.asociarMicrochip(microchipId, mascotaId, conn);

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
