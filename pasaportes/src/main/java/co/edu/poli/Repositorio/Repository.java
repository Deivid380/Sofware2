package co.edu.poli.Repositorio;

import java.util.List;


public interface Repository<T, ID> {    
	String create(T entidad);
	T read(ID id);
	List<T> readAll();
	String update(T entidad);
	String delete(ID id);
}
