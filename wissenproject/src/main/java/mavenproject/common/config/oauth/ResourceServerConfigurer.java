package mavenproject.common.config.oauth;

import mavenproject.common.config.JesimsProperties;
import mavenproject.common.security.handler.AuthExceptionEntryPoint;
import mavenproject.common.security.handler.CustomAccessDeniedHandler;
import mavenproject.modules.sys.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置
 *
 * @author jinghong 2019/10/17
 */
@Configuration
@AllArgsConstructor
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private final JesimsProperties jesimsProperties;
    private final UserService userService;
    private final AuthExceptionEntryPoint authExceptionEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] ignoring = jesimsProperties.getSecurity().getIgnoring();
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(ignoring).permitAll()
                .antMatchers("/**").authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .userDetailsService(userService);

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .resourceId("user");
    }
}
