package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;

/**
 * <pre>
 * グループリストアイテムクラス.
 * </pre>
 * @author res.
 */
@Sqls({
     @Sql(name="find_list_by_login_id",
            sql = "SELECT"
                    + " `GPAC`.`group_id` AS `value`"
                    + ",`GRP`.`name` AS `text`"
                    + " FROM `c_m_account` `ACC`"
                    +   " INNER JOIN `c_r_grp_account` `GPAC` ON `ACC`.`id` =`GPAC`.`account_id`"
                    +   " LEFT OUTER JOIN `c_m_group`  `GRP`  ON `GPAC`.`group_id` =`GRP`.`id`"
                    + " WHERE `ACC`.`login_id` = ?"
                    + " ORDER BY `GPAC`.`account_seq` ASC, `GPAC`.`group_id` ASC")
     ,@Sql(name="find_list_by_account_id",
             sql = "SELECT"
                     + " `GPAC`.`group_id` AS `value`"
                     + ",`GRP`.`name` AS `text`"
                     + " FROM `c_m_account` `ACC`"
                     +   " INNER JOIN `c_r_grp_account` `GPAC` ON `ACC`.`id` =`GPAC`.`account_id`"
                     +   " LEFT OUTER JOIN `c_m_group`  `GRP`  ON `GPAC`.`group_id` =`GRP`.`id`"
                     + " WHERE `ACC`.`id` = ?"
                     + " ORDER BY `GPAC`.`account_seq` ASC, `GPAC`.`group_id` ASC")
    ,@Sql(name="check_group_id_by_account_id",
            sql = "SELECT COUNT(*) AS `count` "
                    + " FROM `c_r_grp_account` `GPAC`"
                    + " WHERE `GPAC`.`account_id` = ?")
})
public class GroupItem extends ListItem {

    /** 件数. */
    private Integer count;

    //-- setter / getter. --//
    /** 件数 を取得します. */
    public Integer getCount() { return count; }
    /** 件数 を設定します. */
    public void setCount(Integer count) { this.count = count; }

}
