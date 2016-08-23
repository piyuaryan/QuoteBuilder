package com.solutionwerk.qb.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModelViewController {
    @RequestMapping({"/", "/login"})
    public ModelAndView root(ModelAndView mv) {
        mv.setViewName("login");
        return mv;
    }
}
