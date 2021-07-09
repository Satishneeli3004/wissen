package mavenproject.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author jinghong 2019/10/17 08:48
 */
@Slf4j
@Component
public class CustomApplicationRunner implements ApplicationRunner {

    @Value("${server.port:8080}")
    private String serverPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("\n----------------------------------------------------------\n" +
                        "Application is running! Access URLs:\n" +
                        "Local: \t\thttp://localhost:{}/index.html\n" +
                        "External: \thttp://{}:{}/index.html\n" +
                        "Doc: \t\thttp://localhost:{}/swagger-ui.html\n" +
                        "----------------------------------------------------------",
                serverPort,
                InetAddress.getLocalHost().getHostAddress(),
                serverPort,
                serverPort);
    }
}