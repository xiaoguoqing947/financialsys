package cn.xgq.financialsys.domain.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicChartsInfo {

    public PublicChartsInfo(String year) {
        this.xCategories= new String[12];
        for(int i=1;i<=12;i++){
            xCategories[i-1]=year+"-"+String.valueOf(i);
        }
    }

    public PublicChartsInfo(int days) {
        this.xCategories= new String[days];
        for(int i=1;i<=days;i++){
            xCategories[i-1]=String.valueOf(i);
        }
    }

    /**
     * 横坐标的数据
     */
    private String[] xCategories;
    /**
     * 横坐标名称
     */
    private String xText;
    /**
     * 纵坐标名称
     */
    private String yText;
    /**
     * 系列名称
     */
    private String seriesName;
    /**
     * 报表（纵坐标）值
     */
    private double[] chartData;
}
