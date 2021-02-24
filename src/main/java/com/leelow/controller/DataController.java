package com.leelow.controller;

import com.leelow.bean.DataBean;
import com.leelow.service.DataService;

import com.leelow.service.DataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;
    @GetMapping("/")
    public String list(Model model){
        List<DataBean> dataList = dataService.list();
        model.addAttribute("dataList",dataList);
        return "list";
    }
}
