package ui;

import usuarios.Usuario;

public class Sesion {

	private String id;

	private Usuario usuario;

	public Sesion(String id, Usuario usuario) {
		this.id = id;
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getId() {
		return id;
	}

}
