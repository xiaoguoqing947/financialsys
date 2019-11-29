package cn.xgq.financialsys.domain;

public class UserBudget {
    private Integer id;

    private String username;

    private Double mIncTotalPrice;

    private Double mExpMaxPrice;

    private Double mExpSuitPrice;

    private Double mExpJoyPrice;

    private Double mExpShopPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Double getmIncTotalPrice() {
        return mIncTotalPrice;
    }

    public void setmIncTotalPrice(Double mIncTotalPrice) {
        this.mIncTotalPrice = mIncTotalPrice;
    }

    public Double getmExpMaxPrice() {
        return mExpMaxPrice;
    }

    public void setmExpMaxPrice(Double mExpMaxPrice) {
        this.mExpMaxPrice = mExpMaxPrice;
    }

    public Double getmExpSuitPrice() {
        return mExpSuitPrice;
    }

    public void setmExpSuitPrice(Double mExpSuitPrice) {
        this.mExpSuitPrice = mExpSuitPrice;
    }

    public Double getmExpJoyPrice() {
        return mExpJoyPrice;
    }

    public void setmExpJoyPrice(Double mExpJoyPrice) {
        this.mExpJoyPrice = mExpJoyPrice;
    }

    public Double getmExpShopPrice() {
        return mExpShopPrice;
    }

    public void setmExpShopPrice(Double mExpShopPrice) {
        this.mExpShopPrice = mExpShopPrice;
    }
}