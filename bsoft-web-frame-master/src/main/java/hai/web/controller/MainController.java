package hai.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shenwb
 * @date 2016-2-16
 * @describe
 */
@Controller
public class MainController {

	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		model.put("title", "demo");
		model.put("page", "bsoft.hai.demo.pages.demoPage");
		return "main.html";
	}
}
