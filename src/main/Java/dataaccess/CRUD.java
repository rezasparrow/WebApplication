package dataaccess;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public interface CRUD<T extends  Customer> {
    public Customer create(T customer) throws SQLException, ClassNotFoundException, IOException;
    public void delete(int id) throws SQLException, ClassNotFoundException, IOException;
    public List<T> all(T customer) ;
    public List<T> all() ;
    public List<T> findById(int id) ;
    public T update(int id , T customer) throws SQLException, ClassNotFoundException;
}
