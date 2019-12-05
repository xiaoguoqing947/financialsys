package cn.xgq.financialsys.util;

import cn.xgq.financialsys.domain.ExpendType;
import cn.xgq.financialsys.domain.UserBudget;
import cn.xgq.financialsys.domain.vo.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonUtils {
    public static double[] getExpChartData(List<VoExpStics> lists, int count, int mouthOrDay) {
        double[] chartData = new double[count];
        List<Integer> listVoTime = null;
        for (VoExpStics vo : lists) {
//            System.out.println(vo.getExpendDate() + "-" + vo.getExpendPrice() + "-" + vo.getExpendTypeId());
            listVoTime = DateUtils.getNumberInStr(vo.getExpendDate());
//            System.out.println("显示月份（几月份：2）：" + listVoTime.get(1));
            for (int i = 0; i < count; i++) {
                if (listVoTime.get(mouthOrDay) == i + 1) {
                    if (chartData[i] == 0) {
                        chartData[i] = vo.getExpendPrice();
                        break;
                    } else {
                        chartData[i] += vo.getExpendPrice();
                        break;
                    }
                }
            }
        }
        return chartData;
    }

    //mouthOrDay  0 year  1 mouth  2 day
    public static double[] getIncChartData(List<VoIncStics> voIncSticsList, int count, int mouthOrDay) {
        double[] chartData = new double[count];
        List<Integer> listVoTime = null;
        for (VoIncStics vo : voIncSticsList) {
            listVoTime = DateUtils.getNumberInStr(vo.getIncomeDate());
            for (int i = 0; i < count; i++) {
                if (listVoTime.get(mouthOrDay) == i + 1) {
                    if (chartData[i] == 0) {
                        chartData[i] = vo.getIncomePrice();
                        break;
                    } else {
                        chartData[i] += vo.getIncomePrice();
                        break;
                    }
                }
            }
        }
        return chartData;
    }

    public static UContextPieChart getUContextPieData(List<VoExpPieChart> voExpPieCharts, List<ExpendType> expendTypeList) {
        double[] chartData = new double[expendTypeList.size()];
        String[] labels = new String[expendTypeList.size()];
/*        double expMaxPrice = 0;
        double expMinPrice = 0;*/
        for (int i = 0; i < expendTypeList.size(); i++) {
            labels[i] = expendTypeList.get(i).getEnpendType();
        }
        for (int i = 0; i < voExpPieCharts.size(); i++) {
            for (int j = 0; j < expendTypeList.size(); j++) {
                if (labels[j].equals(voExpPieCharts.get(i).getExpendType())) {
                    if (voExpPieCharts.get(i).getExpendPrice() == null) {
                        chartData[j] = 0;
                        break;
                    } else {
                        chartData[j] += voExpPieCharts.get(i).getExpendPrice();
                        break;
                    }
                }
            }
        }
        Map<String, Double> map = getMaxAndMin(chartData);
        UContextPieChart uContextPieChart = new UContextPieChart(labels, chartData, map.get("max"), map.get("min"));
        return uContextPieChart;
    }

    public static Map<String, Double> getMaxAndMin(double[] a) {
        Map<String, Double> map = new HashMap<String, Double>();
        double max = a[0], min = a[0];
        for (int i = 1; i <= a.length - 1; i += 2) {
            if (i + 1 >= a.length) {
                if (a[i] > max) {
                    max = a[i];
                }
                if (a[i] < min) {
                    min = a[i];
                }
            }//
            else if (a[i] > a[i + 1]) {
                if (a[i] > max) {
                    max = a[i];
                }
                if (a[i + 1] < min) {
                    min = a[i + 1];
                }
            }//
            else if (a[i] < a[i + 1]) {
                if (a[i + 1] > max) {
                    max = a[i + 1];
                }
                if (a[i] < min) {
                    min = a[i];
                }
            }//
        }
        map.put("max", max);
        map.put("min", min);
        return map;
    }

    public static VoExpDataAnalysis getExpDataAnalysis(VoExpPrice voExpPrice, UserBudget userBudget) {
        double expMaxPriceRate =divide(voExpPrice.getTotalExpPrice(),userBudget.getmExpMaxPrice());
        double expSuitPriceRate = divide(voExpPrice.getTotalExpPrice() , userBudget.getmExpSuitPrice());
        double expJoyPriceRate =divide( voExpPrice.getJoyExpPrice() , userBudget.getmExpJoyPrice());
        double expShopPriceRate = divide(voExpPrice.getShopExpPrice() , userBudget.getmExpShopPrice());
        double incTotalPriceRate =divide( userBudget.getmIncTotalPrice()-voExpPrice.getTotalExpPrice() , userBudget.getmIncTotalPrice());
        VoExpDataAnalysis voExpDataAnalysis = new VoExpDataAnalysis(expMaxPriceRate, expSuitPriceRate, expJoyPriceRate, expShopPriceRate, incTotalPriceRate);
        return voExpDataAnalysis;
    }

    /*两个double相除保留两位小数*/
    public static double divide(double a, double b) {
        BigDecimal bd1 = new BigDecimal(Double.toString(a));
        BigDecimal bd2 = new BigDecimal(Double.toString(b));
        return bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
