package sergiorus.dad.practicadad;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class ClienteEntity {
	
	//identificador
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//parametros
	private String name;
	private String email;
	private String password;
	private String dni;
	
	//lista de pedidos
	@OneToMany(mappedBy="cliente")
	private List<PedidoEntity> pedidos;
	
	//lista de roles del usuario
	@ElementCollection(fetch = FetchType.EAGER)
	List<String> roles;
	
	public ClienteEntity() {}
	
	public ClienteEntity(String nombre, String correo, String contrasena, String dni, String rol) 
	{
		this.setName(nombre);
		this.setEmail(correo);
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String contrasenaCrypt = passwordEncoder.encode(contrasena);
		
		this.setPassword(contrasenaCrypt);
		this.setDni(dni);
		pedidos = new ArrayList<PedidoEntity>();
		roles = new ArrayList<String>();
		roles.add(rol);
	}
	
	//getters y setters
	
	public List<String> getRoles()
	{
		return roles;
	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String nombre) 
	{
		this.name = nombre;
	}

	public String getDni() 
	{
		return dni;
	}

	public void setDni(String dni) 
	{
		this.dni = dni;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String correo) 
	{
		this.email = correo;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String contrasena) 
	{
		this.password = contrasena;
	}
	
	@Override
	public int hashCode() 
	{
		final int primenumber = 31;
		int result = 1;
		result = primenumber * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object object) 
	{
		if(this == object)
			return true;
		if(object == null)
			return false;
		if(getClass() != object.getClass())
			return false;
		ClienteEntity otherClient = (ClienteEntity) object;
		if(name == null) {
			if(otherClient.name != null)
				return false;
		}else if(!name.equals(otherClient.getName())) {
			return false;
		}
		
		return true;
	}
	
}