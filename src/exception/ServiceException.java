/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author manu7
 */
public class ServiceException extends Exception {
  
    public ServiceException(String mensaje) {
      super(mensaje);
    }
    
    public ServiceException(String mensaje, Throwable causa) {
      super(mensaje, causa);
    }
}