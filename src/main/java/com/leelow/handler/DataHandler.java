package com.leelow.handler;

import com.google.gson.Gson;
import com.leelow.bean.DataBean;
import com.leelow.service.DataService;
import com.leelow.util.HttpConnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
public class DataHandler {

    @Autowired
    private DataService dataService;


    public static void main(String[] args) {
        getData();
    }

    @PostConstruct
    public void saveData(){
        System.out.println("初始化数据的存储");
        List<DataBean> dataBeans = getData();

        dataService.remove(null);
        dataService.saveBatch(dataBeans);

    }
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void updateData(){
        System.out.println("更新数据，时间为"+dateFormat.format(new Date()));
        List<DataBean> dataBeans = getData();

        dataService.remove(null);
        dataService.saveBatch(dataBeans);

    }
    public static List<DataBean> getData() {
        String url = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
//        StringBuilder builder = new StringBuilder();
//        try{
//            FileReader fr = new FileReader("tmp.json");
//            char[] cBuff = new char[1024];
//            int cRead = 0;
//            while((cRead = fr.read(cBuff))>0){
//                builder.append(new String(cBuff,0,cRead));
//            }
//            fr.close();
//        }catch (Exception e){
//
//        }
        String str = HttpConnUtil.doGet(url);


        Gson gson = new Gson();
        Map map = gson.fromJson(str,Map.class);

        String substr =(String) map.get("data");
        Map subMap = gson.fromJson(substr,Map.class);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map)areaList.get(0);

        ArrayList<DataBean> resultList = new ArrayList();

        ArrayList childrenList = (ArrayList)dataMap.get("children");
        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");
            Map totalMap = (Map) tmp.get("total");

            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double confirm = (Double) totalMap.get("confirm");
//            double suspect = (Double) totalMap.get("suspect");
            double dead = (Double) totalMap.get("dead");
            double heal = (Double) totalMap.get("heal");
            DataBean dataBean = new DataBean(null,name,(int)nowConfirm,(int)confirm,
                    (int)dead,(int)heal);

            resultList.add(dataBean);

        }

//        System.out.println(resultList);


        return resultList;
        //     System.out.println(builder.toString());
    }
}
