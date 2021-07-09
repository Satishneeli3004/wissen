package mavenproject.common.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 *
 * @author jinghong
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(oauth()))
                .securityContexts(Lists.newArrayList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("接口文档")
                //创建人
                .contact(new Contact("jinghong", "https://jesims.cn", "153335058@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API文档")
                .build();
    }

    @Bean
    List<GrantType> grantTypes() {
        String tokenUrl = "/oauth/token";
        List<GrantType> grantTypes = new ArrayList<>();
        ResourceOwnerPasswordCredentialsGrant ownerPasswordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(tokenUrl);
        grantTypes.add(ownerPasswordCredentialsGrant);
        return grantTypes;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        return Lists.newArrayList(
                new SecurityReference("oauth2", scopes().toArray(new AuthorizationScope[0])));
    }

    private SecurityScheme oauth() {
        return new OAuthBuilder()
                .name("oauth2")
                .scopes(scopes())
                .grantTypes(grantTypes())
                .build();
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> list = new ArrayList();
        list.add(new AuthorizationScope("all", "Grants all access"));
        return list;
    }
}