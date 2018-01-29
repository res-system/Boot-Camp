package com.res_system.re_employee_manager.model.modal_group;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.re_employee_manager.commons.model.entities.table.CMGroup;

/**
 * グループ選択ダイアログ用 グループマスタ Entityクラス.
 * @author res.
 */
@Sqls({
     @Sql(name="find_group_list_for_system",
            sql = "SELECT"
                    + " `GRP`.`id` AS `id`"
                    + ",`GRP`.`code` AS `code`"
                    + ",`GRP`.`name` AS `name`"
                    + ",0 AS `account_seq`"
                    // -- キーワード検索項目. --//
                    + ",IFNULL(`GRP`.`name`,'') AS `keyword`"
                    + " FROM `c_m_group` `GRP`")
     ,@Sql(name="find_group_list_for_root",
            sql = "SELECT"
                    + " `GRP`.`id` AS `id`"
                    + ",`GRP`.`code` AS `code`"
                    + ",`GRP`.`name` AS `name`"
                    + ",0 AS `account_seq`"
                    // -- キーワード検索項目. --//
                    + ",IFNULL(`GRP`.`name`,'') AS `keyword`"
                    + " FROM `c_m_group` `GRP`"
                    + " WHERE `GRP`.`id` IN ("
                    +       "SELECT `GPCN`.`descendant_id`"
                    +       "  FROM `c_r_grp_construction` `GPCN`"
                    +       " WHERE `GPCN`.`ancestor_id` = "
                    +       " (SELECT `root_group_id` FROM `c_m_group` WHERE `id` = ?) "
                    +       ")")
     ,@Sql(name="find_group_list_for_account",
             sql = "SELECT"
                     + " `GRP`.`id` AS `id`"
                     + ",`GRP`.`code` AS `code`"
                     + ",`GRP`.`name` AS `name`"
                     + ",`GPAC`.`account_seq` AS `account_seq`"
                     // -- キーワード検索項目. --//
                     + ",IFNULL(`GRP`.`name`,'') AS `keyword`"
                     + " FROM `c_r_grp_account` `GPAC`"
                     +   " LEFT OUTER JOIN `c_m_group` `GRP` ON `GPAC`.`group_id` =`GRP`.`id`"
                     + " WHERE `GPAC`.`account_id` = ?")
     ,@Sql(name="find_group_list_order",
            sql = " ORDER BY `account_seq` ASC, `id` ASC")
})
public class MdGrpCMGroup extends CMGroup {
}
