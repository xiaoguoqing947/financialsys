package cn.xgq.financialsys.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class UContextSalesChart {
    public UContextSalesChart() {
        this.labels= new String[12];
        for(int i=1;i<=12;i++){
            labels[i-1]=String.valueOf(i)+"月";
        }
    }
    private String[] labels;
    private double[] chartData;

    @Override
    public String toString() {
        return "UContextSalesChart{" +
                "labels=" + Arrays.toString(labels) +
                ", chartData=" + Arrays.toString(chartData) +
                '}';
    }
}
