package co.edu.poli.pasaportes.repositorio;

import java.util.List;

public interface Repository<T, ID> {    
    String create(T entidad, String tipo);
    T read(ID id, String tipo);
    List<T> readAll();
    String update(T entidad, String tipo);
    String delete(ID id, String tipo);
}
