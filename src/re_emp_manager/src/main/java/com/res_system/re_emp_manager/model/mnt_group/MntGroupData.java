package com.res_system.re_emp_manager.model.mnt_group;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.MGroup;

/**
 * グループ情報メンテナンス処理用 グループマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name=SqlMaker.SQL_NAME_SELECT_BY_KEY, sql = ""
            + "--SELECT"
            + ",(CASE WHEN `GRP`.`root_group_id` = `GRP`.`id` THEN '1'"
            +       " ELSE '0' END) AS `is_root`"
            + " --FROM"
            + " WHERE `GRP`.`id` = :id AND `GRP`.`root_group_id` = :root_group_id"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `name` = :name"
            + ",`grp_status` = :grp_status"
            + ",`memo` = :memo"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            + " WHERE `id` = :id AND `root_group_id` = :root_group_id"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_DELETE, sql = ""
            + "--DELETE"
            + " WHERE `id` = :id AND `root_group_id` = :root_group_id"
            )
    ,@Sql(name="get_grp_auth_list", sql = ""
            + "SELECT"
            + " `AUT`.`id` AS `value`"
            + ",`AUT`.`name` AS `text`"
            + " FROM `m_authority` `AUT`"
            + " WHERE 300  < `AUT`.`id` AND `AUT`.`id` < 400"
            + " ORDER BY `id`"
            )
    ,@Sql(name="check_grp_auth", sql = ""
            + "SELECT"
            + " COUNT(*) AS `count`"
            + " FROM `m_authority` `AUT`"
            + " WHERE 300  < `AUT`.`id` AND `AUT`.`id` < 400"
            +   " AND `AUT`.`id` = ?"
            )
    ,@Sql(name="delete_grp_member", sql = ""
            + "DELETE FROM `r_grp_member` WHERE `group_id` = ?"
            )
    ,@Sql(name="delete_login", sql = ""
            + "DELETE FROM `t_login` WHERE `group_id` = ?"
            )
    ,@Sql(name="is_root_group", sql = ""
            + "SELECT COUNT(*) AS `count` --FROM"
            + " WHERE `GRP`.`root_group_id` = ?"
            )
})
public class MntGroupData extends MGroup {

    //---------------------------------------------- properies [private].
    /** ルートフラグ. */
    @Param
    private String is_root;

    //-- setter / getter. --//
    /** ルートフラグ を取得します. */
    public String getIs_root() { return is_root; }
    /** ルートフラグ を設定します. */
    public void setIs_root(String is_root) { this.is_root = is_root; }

}
