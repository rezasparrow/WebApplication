package dataaccess;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public interface CRUD<T extends  Customer> {
    Customer create(T customer) throws SQLException, ClassNotFoundException, IOException;
    void delete(int id) throws SQLException, ClassNotFoundException, IOException;
    List<T> all(T customer) throws SQLException;
    List<T> all() throws SQLException;
    List<T> findById(int id) ;
    List<T> update(int id, T customer) throws SQLException, ClassNotFoundException;
}
