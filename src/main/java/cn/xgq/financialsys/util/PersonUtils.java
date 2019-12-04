package cn.xgq.financialsys.util;

import cn.xgq.financialsys.domain.vo.VoExpStics;
import cn.xgq.financialsys.domain.vo.VoIncStics;

import java.util.List;

public class PersonUtils {
    public static double[] getExpChartData(List<VoExpStics> lists,int count,int mouthOrDay) {
        double[] chartData = new double[count];
        List<Integer> listVoTime = null;
        for (VoExpStics vo : lists) {
//            System.out.println(vo.getExpendDate() + "-" + vo.getExpendPrice() + "-" + vo.getExpendTypeId());
            listVoTime = DateUtils.getNumberInStr(vo.getExpendDate());
//            System.out.println("显示月份（几月份：2）：" + listVoTime.get(1));
            for(int i=0;i<count;i++){
                if (listVoTime.get(mouthOrDay) == i+1) {
                    if(chartData[i] == 0){
                         chartData[i]=vo.getExpendPrice();break;
                    }else {
                        chartData[i]+=vo.getExpendPrice();break;
                    }
                }
            }
        }
        return  chartData;
    }
    //mouthOrDay  0 year  1 mouth  2 day
    public static double[] getIncChartData(List<VoIncStics> voIncSticsList, int count,int mouthOrDay) {
        double[] chartData = new double[count];
        List<Integer> listVoTime = null;
        for (VoIncStics vo : voIncSticsList) {
            listVoTime = DateUtils.getNumberInStr(vo.getIncomeDate());
            for(int i=0;i<count;i++){
                if (listVoTime.get(mouthOrDay) == i+1) {
                    if(chartData[i] == 0){
                         chartData[i]=vo.getIncomePrice();break;
                    }else {
                        chartData[i]+=vo.getIncomePrice();break;
                    }
                }
            }
        }
        return  chartData;
    }
}
