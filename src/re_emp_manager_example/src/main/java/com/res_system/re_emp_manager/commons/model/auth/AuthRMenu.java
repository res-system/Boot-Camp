package com.res_system.re_emp_manager.commons.model.auth;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.RMenu;

/**
 * 認証処理用 メニュー関連マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_side_menu", sql = ""
            + "SELECT"
            + " `MNU`.`no` AS `no`"
            + ",`MNU`.`seq` AS `seq`"
            + ",`MNU`.`level` AS `level`"
            + ",`MNU`.`status` AS `status`"
            + ",`PER`.`key` AS `permission_key`"
            + ",IFNULL(`MNU`.`url`, `PER`.`default_url`) AS `url`"
            + ",IFNULL(`MNU`.`title`, `PER`.`name`) AS `title`"
            + ",IFNULL(IFNULL(`MNU`.`description`, `PER`.`memo`), '') AS `description`"
            + ",IFNULL(`MNU`.`icon`, '') AS `icon`"
            + " FROM `r_menu` `MNU`"
            +   " LEFT OUTER JOIN `m_permission` `PER` ON `PER`.`id` = `MNU`.`permission_id`"
            +   " LEFT OUTER JOIN `r_grant` `GRT` ON `GRT`.`permission_id` = `MNU`.`permission_id`"
            + " WHERE `MNU`.`status` = '0'"
            +   " AND `MNU`.`no` = ?"
            +   " AND (`MNU`.`permission_id` IS NULL OR `GRT`.`authority_id` = ?)"
            +   " AND (`GRT`.`kbn` IS NULL OR `GRT`.`kbn` = ?)"
            +   " ORDER BY `MNU`.`seq` ASC"
            )
})
public class AuthRMenu extends RMenu {

    //---------------------------------------------- properies [private].
    /** 許可キー. */
    @Param
    private String permission_key;
    /** 備考. */
    @Param
    private String memo;

    //-- setter / getter. --//
    /** 許可キー を取得します. */
    public String getPermission_key() { return permission_key; }
    /** 許可キー を設定します. */
    public void setPermission_key(String permission_key) { this.permission_key = permission_key; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }

}
