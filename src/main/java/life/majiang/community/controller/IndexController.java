package life.majiang.community.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller                   //自动识别扫描当前的类，把它当成spring的并举管理，同时识别它为一个controller。
                              // controller：允许这个类接收前端的请求
/**
public class IndexController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name, Model model){   //RequestParam:请求参数.Ctrl+P：快捷键，提示应该传扫描参数
        model.addAttribute("name",name);                                       // 浏览器中的值，存放到Model中。
        return "index";                                                           //自动去模板目录“templates”寻早“index”模板。
    }
}
*/
public class IndexController {
    @GetMapping("/")  //一个反斜杠，代表根；
    public String index(){
        return "index";
    }
}

