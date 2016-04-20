package dataaccess;

import exception.FieldRequiredException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dotin School1 on 4/20/2016.
 */
public interface CRUD<T extends  Customer> {
    public Customer create(T customer) throws SQLException, ClassNotFoundException, IOException;
    public void delete(int id) throws SQLException, ClassNotFoundException, IOException;
    public List<T> read(T customer) ;
    public T update(int id , T customer) throws SQLException, ClassNotFoundException;
}
