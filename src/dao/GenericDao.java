/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import exception.DaoException;
import java.sql.Connection;
import java.util.List;

/**
 * Interfaz generica para operaciones CRUD
 * @param <T> Tipo de entidad generica
 */
public interface GenericDao<T> {
  /**
   * Crea una nueva entidad en la BD
   * @param entity Entidad a crear (Mascota,Microchip)
   * @param conn Conexion externa
   * @throws DaoException si ocurre un error
   */
  void crear(T entity, Connection conn) throws DaoException;
  
  /**
   * Lee una entidad por su ID
   * @param id Identificador
   * @param conn Conexion Externa
   * @return Entidad encontrada o Null
   * @throws DaoException si ocurre un error
   */
  T leer (Long id, Connection conn) throws DaoException;
  
  /**
   * Lee todas las entidades activas (No eliminadas)
   * @param conn Conexion externa
   * @return Lista de entidades
   * @throws DaoException si ocurre un error
   */
  List<T> leerTodos(Connection conn) throws DaoException;
  
  /**
   * Actualiza una entidad existente
   * @param entity entidad con datos actualizados
   * @param conn conexion externa
   * @throws DaoException si ocurre algun error
   */
  void actualizar(T entity, Connection conn) throws DaoException;
  
  /**
   * Borra una entidad existente
   * @param id Identificador
   * @param conn conexion externa
   * @throws DaoException si ocurre algun error
   */
  void eliminar(Long id, Connection conn) throws DaoException;
}
