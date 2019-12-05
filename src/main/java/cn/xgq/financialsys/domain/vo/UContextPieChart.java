package cn.xgq.financialsys.domain.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UContextPieChart {
    private String[] labels;
    private double[] chartData;
    private double maxPrice;
    private double minPrice;
}
