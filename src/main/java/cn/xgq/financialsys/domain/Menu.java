package cn.xgq.financialsys.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Menu {
    private Integer id;
    @DateTimeFormat(pattern="yyyy年MM月dd日 HH时mm分ss秒")//页面写入数据库时格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date createTime;
    @DateTimeFormat(pattern="yyyy年MM月dd日 HH时mm分ss秒")//页面写入数据库时格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date updateTime;

    private String icon;

    private String name;

    private Integer parentId;

    private Integer priority;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}