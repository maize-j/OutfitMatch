package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmSysPermission
 * @date 2022/1/12 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmSysPermission implements Serializable {

    private int permissionId;
    private String name;
    private int parentId;
    private String permission;
    private String url;
    private List<OmSysRole> roles;

}
