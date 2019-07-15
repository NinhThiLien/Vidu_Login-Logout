package lien.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lien.com.authentication.MyDBAuthenticationService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	MyDBAuthenticationService myDBAuthenticationService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception {
		auth.inMemoryAuthentication().withUser("user1").password("123456").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("123456").roles("USER,ADMIN");
		auth.userDetailsService(myDBAuthenticationService);
	}
	
	@Override
	   protected void configure(HttpSecurity http) throws Exception {
	 
	       http.csrf().disable();
	  
	       http.authorizeRequests().antMatchers("/", "/welcome", "/login", "/logout").permitAll();
	  
	       // Trang /userInfo yÃªu cáº§u pháº£i login vá»›i vai trÃ² USER hoáº·c ADMIN.
	       // Náº¿u chÆ°a login, nÃ³ sáº½ redirect tá»›i trang /login.
	       http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
	 
	       // For ADMIN only.
	       // Trang chá»‰ dÃ nh cho ADMIN
	       http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
	 
	  
	       // Khi ngÆ°á»�i dÃ¹ng Ä‘Ã£ login, vá»›i vai trÃ² XX.
	       // NhÆ°ng truy cáº­p vÃ o trang yÃªu cáº§u vai trÃ² YY,
	       // Ngoáº¡i lá»‡ AccessDeniedException sáº½ nÃ©m ra.
	       http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
	 
	  
	       // Cáº¥u hÃ¬nh cho Login Form.
	       http.authorizeRequests().and().formLogin()//
	  
	               // Submit URL cá»§a trang login
	               .loginProcessingUrl("/j_spring_security_check") // Submit URL
	               .loginPage("/login")//
	               .defaultSuccessUrl("/userInfo")//
	               .failureUrl("/login?error=true")//
	               .usernameParameter("userName")//
	               .passwordParameter("password")
	  
	               // Cáº¥u hÃ¬nh cho Logout Page.
	               .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
	 
	   }
}
