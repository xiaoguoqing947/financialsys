package cn.xgq.financialsys.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
}

