package sergiorus.dad.practicadad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ClienteRepositoryAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private ClienteRepository clientes;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException{
		
		ClienteEntity cliente = clientes.findFirstByName(auth.getName());
		
		if(cliente == null) 
			throw new BadCredentialsException("Cliente no encontrado");
		
		
		String password = (String) auth.getCredentials();
		
		if(!new BCryptPasswordEncoder().matches(password, cliente.getPassword())) 
			throw new BadCredentialsException("Contraseña incorrecta");
	
		List<GrantedAuthority> roles = new ArrayList<>();
		
		for(String role: cliente.getRoles()) 
		{
			roles.add(new SimpleGrantedAuthority(role));
		}
		
		return new UsernamePasswordAuthenticationToken(cliente.getName(), password, roles);
	}
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}
}
