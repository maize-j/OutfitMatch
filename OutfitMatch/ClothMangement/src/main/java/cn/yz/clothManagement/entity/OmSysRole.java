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
 * @ClassName OmSysRole
 * @date 2022/1/12 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmSysRole implements Serializable {
    private int roleId;
    private String description;
    private String role;
    private List<OmSysUser> users;
    private List<OmSysPermission> permissions;
}
