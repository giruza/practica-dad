package sergiorus.dad.practicadad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private ClienteEntity cliente;
	
	@OneToMany
	private List<ProductoEntity> productos;
	
	private String nombre_pedido;
	private String paypal;
	private String direccion_entrega;
	private String municipio;
	
	public PedidoEntity() {}
	
	public PedidoEntity(String nombre, String paypal, String direccion, String municipio, ClienteEntity cliente, List<ProductoEntity> productos) {
		this.setNombre_pedido(nombre);
		this.setPaypal(paypal);
		this.setDireccion_entrega(direccion);
		this.cliente = cliente;
		this.productos = productos;
	}

	public String getNombre_pedido() {
		return nombre_pedido;
	}

	public void setNombre_pedido(String nombre_pedido) {
		this.nombre_pedido = nombre_pedido;
	}

	public String getDireccion_entrega() {
		return direccion_entrega;
	}

	public void setDireccion_entrega(String direccion_entrega) {
		this.direccion_entrega = direccion_entrega;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getPaypal() {
		return paypal;
	}

	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
	
}