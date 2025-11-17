package service;

import dao.MicrochipDao;
import exception.ServiceException;
import model.Microchip;

/**
 * Service para operaciones de negocio de Microchip.
 */
public class MicrochipService extends GenericService<Microchip> {
  
    public MicrochipService() {
        super(new MicrochipDao());
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
}
