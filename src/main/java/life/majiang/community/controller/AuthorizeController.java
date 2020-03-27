package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired  //把spring容器里面写好实例化的实例，加载到当前使用的上下文。
//    @Resource
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    //Value：会去配置文件中读取“github.client.id”的value，赋值给“clientID”。
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
//    @Autowired  //使用“Autowired”会提示警告；解决方法：https://www.cnblogs.com/Howinfun/p/11731826.html；
    @Resource
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,        //P11 Github 登录之获取 code
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) //"HttpServletRequest request"写入方法中，spring自动将上下文中的request，放入此处供使用。
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
//        gitHubProvider.getAccessToken(accessTokenDTO);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = (GithubUser) gitHubProvider.getUser(accessToken);
//        System.out.println(user.getName());
        if (githubUser != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountID(String.valueOf(githubUser.getId()));  //将githubUser.getId()的Long类型，强转成String类型；
            user.setGmtCreate(System.currentTimeMillis());  //用当前的毫秒数计算；
            user.getGmtModfied(user.getGmtCreate());
            System.out.println("-1-");
            userMapper.insert(user);
            System.out.println("-2-");
            // 登录成功，写cookie和session。
            request.getSession().setAttribute("user",githubUser); //user对象，放入session里面。
        }else {

            //登录失败，重新启动。
            //            return "-1-";
        }
        return "redirect:/";  //重定向

    }

}

