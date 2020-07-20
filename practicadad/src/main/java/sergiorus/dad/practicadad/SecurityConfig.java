package sergiorus.dad.practicadad;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity security) throws Exception
	{
		security.authorizeRequests().antMatchers("/").permitAll();
		security.authorizeRequests().antMatchers("/greeting").permitAll();
		
		security.authorizeRequests().anyRequest().authenticated();
		
		security.formLogin().loginPage("/login");
		security.formLogin().usernameParameter("username");
		security.formLogin().passwordParameter("password");
		security.formLogin().defaultSuccessUrl("/greeting");
		security.formLogin().failureUrl("/greeting");
		
		security.logout().logoutUrl("/logout");
		security.logout().logoutSuccessUrl("/greeting");
		
		security.csrf().disable();
	}
	
	@Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	 auth.inMemoryAuthentication().withUser("user").password("pass").roles("USER");
	 
	}
}
