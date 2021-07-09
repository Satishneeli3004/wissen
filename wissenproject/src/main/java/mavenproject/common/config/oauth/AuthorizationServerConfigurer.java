package mavenproject.common.config.oauth;

import mavenproject.common.constant.SecurityConstants;
import mavenproject.common.security.handler.AuthExceptionEntryPoint;
import mavenproject.common.security.handler.CustomAccessDeniedHandler;
import mavenproject.common.security.handler.CustomWebResponseExceptionTranslator;
import mavenproject.modules.sys.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * 认证服务器配置
 *
 * @author jinghong 2019/10/17
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private final CustomWebResponseExceptionTranslator exceptionTranslator;
    private final AuthenticationManager authenticationManager;
    private final AuthExceptionEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final RedisConnectionFactory redisConnectionFactory;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder)
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore())
                .userDetailsService(userService)
                .authenticationManager(authenticationManager)
                .exceptionTranslator(exceptionTranslator);
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(SecurityConstants.OAUTH_PREFIX);
        return tokenStore;
    }
}
