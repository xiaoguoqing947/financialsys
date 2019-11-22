package cn.xgq.financialsys.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class SearchMenuForm {

    @NotNull
    private String menuCode;//这边支持用户名/邮箱/手机号登录 需要判断下
    @NotNull
    private String menuNameLike;
    @NotNull
    private String pageSize;
    @NotNull
    private String currentPage;
    @NotNull
    private int indexCount;

}
