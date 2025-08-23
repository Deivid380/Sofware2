package co.edu.poli.Repositorio;

import java.util.List;


public interface Repository<T, ID> {    
	void create(T entidad);
	T read(ID id);
	List<T> readAll();
	void update(T entidad);
	void delete(ID id);
}
