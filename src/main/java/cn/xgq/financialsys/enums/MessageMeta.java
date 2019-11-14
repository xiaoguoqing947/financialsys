package cn.xgq.financialsys.enums;

public enum MessageMeta {
    sendError("fail"),
    sendSuccess("success");

    // 定义实例对应的参数
    private String msg;
    // 必写：通过此构造器给枚举值创建实例
    MessageMeta(String msg) {
        this.msg = msg;
    }
    // 通过此方法可以获取到对应实例的参数值
    public String getMsg() {
        return msg;
    }
}
