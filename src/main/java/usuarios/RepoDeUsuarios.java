package usuarios;

import javax.persistence.NoResultException;
import modelo.repositorios.persistencia.RepoPersistencia;

public class RepoDeUsuarios extends RepoPersistencia {

	private static RepoDeUsuarios repositorio = null;

	public void agregarUsuario(Usuario usuario) throws ExceptionUsuarioExistente {

		if (this.existeUsuario(usuario.getUsername()))
			throw new ExceptionUsuarioExistente();
		else {
			entityManager().persist(usuario);
		}

	}

	private boolean existeUsuario(String username) {
                try{
                    obtenerUsuario(username);
                    return true;
                } catch(NoResultException ex){
                    return false;
                }
	}

	public Usuario loginExitoso(String username, String password) throws Exception {

		if (password == null || password.isEmpty() || username == null || username.isEmpty())
			throw new Exception("Por favor, ingrese usuario y contrase�a.");

		else if (!this.existeUsuario(username)) {
			throw new Exception("Usuario y contrase�a incorrectos.");
		}

		else {
			Usuario usuario = this.obtenerUsuario(username);
			if (!usuario.getPassword().equals(password))
				throw new Exception("Usuario y contrase�a incorrectos.");
			else
				return usuario;
		}
	}
	
	public static RepoDeUsuarios getInstance() {
		if(repositorio == null)
			repositorio = new RepoDeUsuarios();
		return repositorio;
	}

	private Usuario obtenerUsuario(String nombre) {
		return (Usuario) entityManager().createQuery("from Usuario where username = :user").setParameter("user", nombre)
				.getSingleResult();
	}
        
        public void eliminarTodosLosUsuarios(){
            entityManager().createQuery("delete from Usuario").executeUpdate();
        }

}
