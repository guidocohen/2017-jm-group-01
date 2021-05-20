package modelo.repositorios.persistencia;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


import modelo.metodologias.Metodologia;
import modelo.repositorios.RepoDeMetodologias;

public class RepoDeMetodologiasPersistencia extends RepoPersistencia implements RepoDeMetodologias {
    
        private static RepoDeMetodologiasPersistencia instancia;

        public static RepoDeMetodologiasPersistencia getInstancia() {
            if (instancia == null) {
                instancia = new RepoDeMetodologiasPersistencia();
            }
            return instancia;
        }
        
        @Override
        public Metodologia getById(long id) {
                return entityManager().find(Metodologia.class, id);
        }
    
	@Override
	public Collection<Metodologia> getAll() {
		return entityManager().createQuery("from Metodologia", Metodologia.class).getResultList();
	}

	@Override
	public Metodologia getMetodologiaPorNombre(String nombre) {
		return (Metodologia) entityManager().createQuery("from Metodologia where nombre = :nombre")
				.setParameter("nombre", nombre).getResultList().get(0);
	}

	@Override
	public void agregar(Metodologia metodologia) {
		entityManager().persist(metodologia);
	}

	@Override
	public Map<String, Metodologia> getMetodologias() {
		return getAll().stream().collect(Collectors.toMap(Metodologia::getNombre, Function.identity()));
	}

	@Override
	public void limpiarMetodologias() {
		entityManager().clear();

	}

	public void eliminarMetodologias() {
                entityManager().createQuery("delete from Condicion").executeUpdate();
		entityManager().createQuery("delete from Metodologia").executeUpdate();
	}
        
}
