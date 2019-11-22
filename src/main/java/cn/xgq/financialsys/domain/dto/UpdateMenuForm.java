package cn.xgq.financialsys.domain.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateMenuForm {
    private int updateMenuId;
    private String updateName;
    private String updateUrl;
}
