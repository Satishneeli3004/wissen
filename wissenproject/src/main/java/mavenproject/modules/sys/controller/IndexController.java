package mavenproject.modules.sys.controller;

import mavenproject.modules.framework.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页跳转
 *
 * @author jinghong 2019/10/17
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/index.html";
    }

}
