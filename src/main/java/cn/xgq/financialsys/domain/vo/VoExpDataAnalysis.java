package cn.xgq.financialsys.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoExpDataAnalysis {
    private double expMaxPriceRate;
    private double expSuitPriceRate;
    private double expJoyPriceRate;
    private double expShopPriceRate;
    private double incTotalPriceRate;
}
