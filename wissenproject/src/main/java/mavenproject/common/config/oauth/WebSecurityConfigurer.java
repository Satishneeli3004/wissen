package mavenproject.common.config.oauth;

import mavenproject.common.config.JesimsProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security配置
 *
 * @author jinghong 2019/10/17
 */
@Order(2)
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final JesimsProperties jesimsProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/oauth/**", "/login")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login").loginPage("/login")
                .and()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    @Override
    public void configure(WebSecurity web) {
        String[] ignoring = jesimsProperties.getSecurity().getIgnoring();
        web.ignoring().antMatchers(ignoring);
    }
}
