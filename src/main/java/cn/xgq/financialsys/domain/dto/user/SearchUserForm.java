package cn.xgq.financialsys.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchUserForm {
    @NotNull
    private String userCode;
    @NotNull
    private String username;
    @NotNull
    private String pageSize;
    @NotNull
    private String currentPage;
    @NotNull
    private int indexCount;
}
