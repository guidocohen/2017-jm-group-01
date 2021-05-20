package modelo.repositorios.persistencia;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonMappingException;

import carga.CargadorEmpresas;
import excepciones.FiltrarPorNombreException;
import modelo.Empresa;
import modelo.repositorios.RepoDeEmpresas;

public class RepoDeEmpresasPersistencia extends RepoPersistencia implements RepoDeEmpresas {

	private static RepoDeEmpresasPersistencia instancia;

	protected RepoDeEmpresasPersistencia() {
	}

	public static RepoDeEmpresasPersistencia getInstancia() {
		if (instancia == null)
			instancia = new RepoDeEmpresasPersistencia();
		return instancia;
	}

	@Override
	public void cargarEmpresas(CargadorEmpresas cargador) throws FiltrarPorNombreException, IOException {
		cargador.cargar().forEach(empresa -> entityManager().persist(empresa));
	}

	@Override
	public Empresa obtenerEmpresa(String nombre) {
		return (Empresa) entityManager().createQuery("from Empresa where nombre = :nombre")
				.setParameter("nombre", nombre).getResultList().get(0);
	}

        @Override
        public Empresa obtenerPorId(Integer id) {
            return entityManager().find(Empresa.class, id);
        }
        
	@Override
	@SuppressWarnings("unchecked")
	public List<Empresa> getEmpresas() {
        	return entityManager().createQuery("from Empresa").getResultList();
	}

	public void eliminarEmpresa(Empresa empresaAEliminar) {
		Empresa empresaEncontrada = entityManager().find(Empresa.class, empresaAEliminar.getId());
		entityManager().remove(empresaEncontrada);
	}

	public void actualizarEmpresa(Empresa empresaNueva, int empresaViejaID) {
		Empresa empresaVieja = entityManager().find(Empresa.class, empresaViejaID);
		empresaVieja.setCuentas(empresaNueva.getCuentas());
		empresaVieja.setEdad(empresaNueva.getEdad());
		empresaVieja.setNombre(empresaNueva.getNombre());

	}

	public void eliminarTodasLasEmpresas() {
		entityManager().createQuery("delete from Empresa").executeUpdate();
	}
	
	private void persistir(Empresa empresa) {
		Empresa emp = obtenerEmpresa(empresa.getNombre());
		if(emp == null) {
			entityManager().persist(empresa);
		}else {
			actualizarEmpresa(empresa, emp.getId());
		}
		
	}
	
	public void cargaInicial(CargadorEmpresas cargador) throws JsonMappingException, IOException {
		if(getEmpresas().isEmpty()) {
			entityManager().getTransaction().begin();
			cargador.cargar().forEach(empresa -> entityManager().persist(empresa));
			entityManager().getTransaction().commit();
		} else{
			entityManager().getTransaction().begin();
			cargador.cargar().forEach(empresa -> this.persistir(empresa));
			entityManager().getTransaction().commit();
		}
	}

}
