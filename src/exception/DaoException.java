/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/*Excepcion para errores en el DAO.*/

public class DaoException extends Exception {
  
  public DaoException(String mensaje) {
    super(mensaje);
  }
  
  public DaoException(String mensaje, Throwable causa) {
   super(mensaje, causa); 
  }
  
}
