package cn.xgq.financialsys.util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 76905
 */
public class DateUtils {

    public static String  getDateStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date strToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String preciseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date dateBeforeMoth() {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-14);
        Date date = c.getTime();
        String dateStr = format.format(date);
        Date result = null;
        try {
            result = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //根据年份和月份获取改月的天数
    public static int getDays(int year, int month) {
        int days = 0;
        boolean isLeapYear = false;
        if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
//            System.out.println("--------------------闰年-------------------");
            isLeapYear = true;
        } else {
//            System.out.println("--------------------非闰年-------------------");
            isLeapYear = false;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 2:
                if (isLeapYear) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            default:
                System.out.println("error!!!");
                break;
        }
        return days;
    }


    //获取字符串中的数字  list数组下标  0 ：年份  1：月份  2：第几天
    public static List<Integer> getNumberInStr(String s){
        List<Integer> list=new ArrayList<Integer>();
        Pattern p = Pattern.compile("\\d{1,}");
        Matcher m = p.matcher(s);
        while(m.find()) {
            list.add(Integer.parseInt(m.group()));
        }
        return list;
    }
}

