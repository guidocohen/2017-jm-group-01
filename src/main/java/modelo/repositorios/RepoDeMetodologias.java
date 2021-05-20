package modelo.repositorios;

import java.util.Collection;

import java.util.Map;

import modelo.metodologias.Metodologia;

public interface RepoDeMetodologias {
    
        public Metodologia getById(long id);

	public Collection<Metodologia> getAll();

	public Metodologia getMetodologiaPorNombre(String nombreMetodologia);

	public void agregar(Metodologia metodologia);

	public Map<String, Metodologia> getMetodologias();

	public void limpiarMetodologias();
}
