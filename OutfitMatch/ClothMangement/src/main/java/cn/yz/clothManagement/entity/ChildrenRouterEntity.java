package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ChildrenRouterEntity
 * @date 2022/2/26 18:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenRouterEntity {
    private String title;
    private String type;
    private String name;
    private String route;
    private String filePath;
    private boolean inNav;
    private String icon;
}
