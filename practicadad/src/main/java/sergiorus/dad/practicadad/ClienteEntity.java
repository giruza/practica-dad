package sergiorus.dad.practicadad;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ClienteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String correo;
	private String contrasena;
	private String dni;
	private boolean logeado;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<PedidoEntity> pedidos;
	
	@OneToMany
	private List<ProductoEntity> carro;
	
	public ClienteEntity() {}
	
	public ClienteEntity(String nombre, String correo, String contrasena, String dni, boolean logeado) {
		this.setNombre(nombre);
		this.setCorreo(correo);
		this.setContrasena(contrasena);
		this.setDni(dni);
		this.setLogeado(logeado);
		pedidos = new ArrayList<PedidoEntity>();
		carro = new ArrayList<ProductoEntity>();
	}

	
	public void alCarro(ProductoEntity producto) {
		this.carro.add(producto);
	}
	
	public void vaciarCarro() {
		this.carro.clear();
	}
	
	//getters y setters
	
	public List<ProductoEntity> getCarro() {
		return carro;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public boolean isLogeado() {
		return logeado;
	}

	public void setLogeado(boolean logeado) {
		this.logeado = logeado;
	}
	
}