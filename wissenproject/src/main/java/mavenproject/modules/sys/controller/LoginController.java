package mavenproject.modules.sys.controller;

import mavenproject.modules.framework.controller.BaseController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 登录相关
 *
 * @author jinghong
 */
@Slf4j
@Controller
@AllArgsConstructor
public class LoginController extends BaseController {

    private final ClientDetailsService clientDetailsService;

    @GetMapping("/login")
    public String login() {
        return "ftl/login";
    }

    @GetMapping("/confirm_access")
    public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
        Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
        modelAndView.addObject("scopeList", scopeList.keySet());

        Object auth = session.getAttribute("authorizationRequest");
        if (auth != null) {
            AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
            modelAndView.addObject("app", clientDetails.getAdditionalInformation());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            modelAndView.addObject("user", principal);
        }

        modelAndView.setViewName("ftl/confirm");
        return modelAndView;
    }
}
