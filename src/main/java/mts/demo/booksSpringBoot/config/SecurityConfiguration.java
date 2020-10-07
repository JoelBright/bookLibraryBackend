package mts.demo.booksSpringBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * This section defines the user accounts which can be used for
   * authentication as well as the roles each user has.
   */
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication()
      .withUser("greg").password("turnquist").roles("USER").and()
      .withUser("ollie").password("gierke").roles("USER", "ADMIN");
  }

  /**
   * This section defines the security policy for the app.
   * - BASIC authentication is supported
   * - /books is secured using URL security shown below
   * - CSRF headers are disabled since we are only using the REST interface,
   *   not a web one.
   *
   * NOTE: GET is not shown which defaults to permitted.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
      .httpBasic().and()
      .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN").and()
      .csrf().disable();
  }
}