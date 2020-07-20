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

@Entity
public class ClienteEntity {
	
	//identificador
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//parametros
	private String name;
	private String correo;
	private String contrasena;
	private String dni;
	
	//lista de pedidos
	@OneToMany(mappedBy="cliente")
	private List<PedidoEntity> pedidos;
	
	//lista de productos en el carro
	@OneToMany
	private List<ProductoEntity> carro;
	
	private String passwordHash;
	
	//lista de roles del usuario
	@ElementCollection(fetch = FetchType.EAGER)
	List<String> roles;
	
	public ClienteEntity() {}
	
	public ClienteEntity(String name, String correo, String contrasena, String dni, String rol) {
		this.setName(name);
		this.setCorreo(correo);
		this.setContrasena(contrasena);
		this.setDni(dni);
		pedidos = new ArrayList<PedidoEntity>();
		carro = new ArrayList<ProductoEntity>();
		roles = new ArrayList<String>();
		roles.add(rol);
	}

	
	public void alCarro(ProductoEntity producto) {
		this.carro.add(producto);
	}
	
	public void vaciarCarro() {
		this.carro.clear();
	}
	
	//getters y setters
	
	public List<String> getRoles(){
		return roles;
	}
	
	public List<ProductoEntity> getCarro() {
		return carro;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String nombre) {
		this.name = name;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
}