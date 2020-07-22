package sergiorus.dad.practicadad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		security.authorizeRequests().antMatchers("/login").permitAll();
		security.authorizeRequests().antMatchers("/loginerror").permitAll();
		//...
		
		//paginas privadas
		security.authorizeRequests().antMatchers("/stats").hasAnyRole("ADMIN");
		
		security.authorizeRequests().antMatchers("/store").hasAnyRole("CLIENT");
		security.authorizeRequests().antMatchers("/addproduct").hasAnyRole("CLIENT");
		security.authorizeRequests().antMatchers("/cart").hasAnyRole("CLIENT");
		security.authorizeRequests().antMatchers("/createpetition").hasAnyRole("CLIENT");
		security.authorizeRequests().antMatchers("/submitpetition").hasAnyRole("CLIENT");
		//...
		
		//parametros para el login y pagina de login
		security.formLogin().loginPage("/login");
		security.formLogin().usernameParameter("username");
		security.formLogin().passwordParameter("password");
		security.formLogin().defaultSuccessUrl("/greeting");
		security.formLogin().failureUrl("/loginerror");
		
		//parametros de logout
		security.logout().logoutUrl("/logoutsuccess");
		security.logout().logoutSuccessUrl("/greeting");
		
		//security.csrf().disable();
	}
	
	@Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(authenticationProvider);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
