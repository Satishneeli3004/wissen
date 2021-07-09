package mavenproject.modules.sys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;

/**
 * @author jinghong
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public String test(String uri) {
        log.info("uri {}", uri);
        return "redirect:https://a.com?url=" + URLEncoder.encode(uri);
    }
}
