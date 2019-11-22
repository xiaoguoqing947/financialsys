package cn.xgq.financialsys.util;

import cn.xgq.financialsys.domain.Dictionary;
import cn.xgq.financialsys.service.inter.DicSer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class DicMap {
    @Autowired
    private static DicSer dicSer;
    private static HashMap<String,Object> hashMap=new HashMap<String,Object>();

    public static void queryDic(){
        List<Dictionary> dics=dicSer.queryAll();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<dics.size();i++){
            Dictionary dictionary=dics.get(i);
            String dcisCode=dictionary.getDcisCode();
            String keyValue=dictionary.getKeyValue();
            String key=dcisCode+"_"+keyValue;
            String value=dictionary.getKeyName();
            System.out.println(key+"="+value);
            hashMap.put(key,value);
        }
    }

    public static String getKeyName(String dcisCode,String keyValue){
        StringBuilder sb=new StringBuilder();
        StringBuilder keySb=sb.append(dcisCode).append("_").append(keyValue);
        String key=keySb.toString();
        String value=(String)hashMap.get(key);
        return value;
    }
}
