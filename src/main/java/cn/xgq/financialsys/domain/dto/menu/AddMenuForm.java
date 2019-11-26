package cn.xgq.financialsys.domain.dto.menu;

import lombok.*;

import javax.validation.constraints.NotNull;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddMenuForm {
    @NotNull
    private String name;
    @NotNull
    private int parentId;
    private int priority;
    @NotNull
    private String url;
}
