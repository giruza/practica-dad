package sergiorus.dad.practicadad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public ClienteRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity security) throws Exception
	{
		
		//paginas publicas
		security.authorizeRequests().antMatchers("/").permitAll();
		security.authorizeRequests().antMatchers("/greeting").permitAll();
		security.authorizeRequests().antMatchers("/tienda").permitAll();
		security.authorizeRequests().anyRequest().authenticated();
		//...
		
		//paginas privadas
		security.authorizeRequests().antMatchers("/estadisticas").hasAnyRole("A");
		//...
		
		//parametros para el login y pagina de login
		security.formLogin().loginPage("/login");
		security.formLogin().usernameParameter("username");
		security.formLogin().passwordParameter("password");
		security.formLogin().defaultSuccessUrl("/greeting");
		security.formLogin().failureUrl("/greeting");
		
		//parametros de logout
		security.logout().logoutUrl("/logout");
		security.logout().logoutSuccessUrl("/greeting");
		
		security.csrf().disable();
	}
	
	@Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(authenticationProvider);
		//auth.inMemoryAuthentication().withUser("client").password("pass").roles("CLIENT");
		//auth.inMemoryAuthentication().withUser("admin").password("pass").roles("ADMIN");
	 
	}
}
