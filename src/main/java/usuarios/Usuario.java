package usuarios;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {
    
	@Id
	@GeneratedValue
	private long id;

	private String username;

	private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    protected Usuario() { }
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}


}
