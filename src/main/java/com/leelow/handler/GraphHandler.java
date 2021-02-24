package com.leelow.handler;

import com.google.gson.Gson;
import com.leelow.bean.GraphBarBean;
import com.leelow.bean.GraphBean;
import com.leelow.util.HttpConnUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphHandler {
    public static List<GraphBean> getGraphData() {
        String url = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=chinaDayList,chinaDayAddList,cityStatis,nowConfirmStatis,provinceCompare";
        String str = HttpConnUtil.doGet(url);


        Gson gson = new Gson();
        Map map = gson.fromJson(str,Map.class);

        Map subMap =(Map) map.get("data");

        ArrayList<GraphBean> result = new ArrayList<>();


        ArrayList chinaDayList = (ArrayList) subMap.get("chinaDayList");
        for (int i = 0; i < chinaDayList.size(); i++) {

               Map chinaMap = (Map) chinaDayList.get(i);

                String date =(String) chinaMap.get("date");
                double confirm =(Double) chinaMap.get("confirm");
                double heal =(Double) chinaMap.get("heal");
                double dead =(Double) chinaMap.get("dead");

              GraphBean graphBean = new GraphBean(date,(int)confirm,(int)heal,(int)dead);


              result.add(graphBean);

        }
             return result;
    }
        public static String urlStrAll = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
    public static List<GraphBarBean> getGraphBarData(){
        String str = HttpConnUtil.doGet(urlStrAll);


        Gson gson = new Gson();
        Map map = gson.fromJson(str,Map.class);

        String substr =(String) map.get("data");
        Map subMap = gson.fromJson(substr,Map.class);
        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map)areaList.get(0);
        ArrayList childrenList= (ArrayList) dataMap.get("children");

        ArrayList<GraphBarBean> result = new ArrayList<>();

        for (int i = 0; i < childrenList.size(); i++) {
             Map tmp = (Map) childrenList.get(i);
             String name = (String) tmp.get("name");

           ArrayList children = (ArrayList) tmp.get("children");
            for (int j = 0; j < children.size(); j++) {
                Map subTmp = (Map) children.get(j);
                if("境外输入".equals((String) subTmp.get("name"))){
                    Map total = (Map) subTmp.get("total");
                    double fromAbroad = (Double) total.get("confirm");
                    System.out.println(fromAbroad);
                    GraphBarBean graphBarBean = new GraphBarBean(name,(int)fromAbroad);
                    result.add(graphBarBean);
                }
            }

        }

        return result;
    }
    public static void main(String[] args){
        getGraphBarData();
    }
}
