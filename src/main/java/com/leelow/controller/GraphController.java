package com.leelow.controller;

import com.google.gson.Gson;
import com.leelow.bean.DataBean;
import com.leelow.bean.GraphBarBean;
import com.leelow.bean.GraphBean;
import com.leelow.bean.MapBean;
import com.leelow.handler.GraphHandler;
import com.leelow.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class GraphController {

    @Autowired
    private DataService dataService;

    @GetMapping("/graph")
    public String graph(Model model){
        List<GraphBean> list = GraphHandler.getGraphData();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> confirmList = new ArrayList<>();
        ArrayList<Integer> healList = new ArrayList<>();
        ArrayList<Integer> deadList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
             GraphBean graphBean = list.get(i);
             dateList.add(graphBean.getDate());
             confirmList.add(graphBean.getConfirm());
             healList.add(graphBean.getHeal());
             deadList.add(graphBean.getDead());
        }

        model.addAttribute("dateList",new Gson().toJson(dateList));
        model.addAttribute("confirmList",new Gson().toJson(confirmList));
        model.addAttribute("healList",new Gson().toJson(healList));
        model.addAttribute("deadList",new Gson().toJson(deadList));


        return "graph";
    }

    @GetMapping("/graphBar")
    public String graphBar(Model model){
        List<GraphBarBean> list = GraphHandler.getGraphBarData();
        Collections.sort(list);
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Integer> fromAbroadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GraphBarBean bean = list.get(i);
            nameList.add(bean.getName());
            fromAbroadList.add(bean.getFromAbroad());

        }

        model.addAttribute("nameList",new Gson().toJson(nameList));
        model.addAttribute("fromAbroadList",new Gson().toJson(fromAbroadList));

        return "graphBar";
    }


    @GetMapping("/map")
    public String map(Model model){
        List<DataBean> list = dataService.list();
        ArrayList<MapBean> maplist1 = new ArrayList<>();
        ArrayList<MapBean> mapList2 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            DataBean dataBean = list.get(i);
            MapBean mapBean = new MapBean(dataBean.getName(),dataBean.getNowConfirm());
            maplist1.add(mapBean);
            MapBean mapBean1 = new MapBean(dataBean.getName(),dataBean.getConfirm());
            mapList2.add(mapBean1);

        }

        model.addAttribute("dataMap1",new Gson().toJson(maplist1));
        model.addAttribute("dataMap2",new Gson().toJson(mapList2));
        return "map";
    }


}
