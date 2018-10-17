package com.res_system.re_emp_manager.model.select_group;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.MGroup;

/**
 * グループ選択処理用 グループマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `GRP`.`id` AS `id`"
            + ",`GRP`.`code` AS `code`"
            + ",`GRP`.`name` AS `name`"
            + ",`GRP`.`root_group_id` AS `root_group_id`"
            + ",`GRP`.`owner_account_id` AS `owner_account_id`"
            + ",`GRP`.`grp_status` AS `grp_status`"
            + ",`GrpStat`.`name` AS `grp_status_name`"
            + ",`GRP`.`memo` AS `memo`"
            + ",(SELECT COUNT(*) FROM `r_grp_member` `GRPMB` WHERE `GRPMB`.`group_id` = `GRP`.`id`) AS `member_count`"
            + ",DATE_FORMAT(`GRP`.`updated`, '%Y/%m/%d %H:%i:%s') AS `updated`"
            + ",(CASE WHEN `GRP`.`root_group_id` = `GRP`.`id` THEN '1'"
            +       " ELSE '0' END) AS `is_root`"
            // -- キーワード検索項目 start. --//
            + ",CONCAT(IFNULL(`GRP`.`name`,'')"
            +    ",' ',IFNULL(`GrpStat`.`name`,'')"
            +    ",' ',IFNULL(`GRP`.`memo`,'')"
            +    ") AS `keyword`"
            // -- キーワード検索項目 end  .--//
            + " FROM `m_group` `GRP`"
            +   " LEFT OUTER JOIN `g_kind` `GrpStat`"
            +       " ON `GrpStat`.`kbn` = 'GrpStat' AND `GrpStat`.`seq` <> 0 AND `GrpStat`.`code` = `GRP`.`grp_status`"
            + " WHERE `GRP`.`root_group_id` = ?"
            )
    ,@Sql(name="find_list_order", sql = "ORDER BY `is_root` DESC, `name` ASC ")
    ,@Sql(name="find_list_order_1", sql = "ORDER BY `name` ASC ")
    ,@Sql(name="find_list_order_2", sql = "ORDER BY `name` DESC ")
    ,@Sql(name="find_list_order_3", sql = "ORDER BY `member_count` ASC ")
    ,@Sql(name="find_list_order_4", sql = "ORDER BY `member_count` DESC ")
    ,@Sql(name="find_list_order_5", sql = "ORDER BY `grp_status` ASC ")
    ,@Sql(name="find_list_order_6", sql = "ORDER BY `grp_status` DESC ")
    ,@Sql(name="find_list_order_7", sql = "ORDER BY `memo` ASC ")
    ,@Sql(name="find_list_order_8", sql = "ORDER BY `memo` DESC ")
    ,@Sql(name="find_status", sql = "`grp_status` IN ('0','1')")
    ,@Sql(name="check_count", sql = ""
            + "SELECT COUNT(`GRP`.`id`) AS `count`"
            + " --FROM"
            + " WHERE `GRP`.`root_group_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_SELECT_BY_KEY, sql = ""
            + "SELECT"
            + " `GRP`.`id` AS `id`"
            + ",`GRP`.`code` AS `code`"
            + ",`GRP`.`name` AS `name`"
            + ",`GRP`.`root_group_id` AS `root_group_id`"
            + ",`GRP`.`owner_account_id` AS `owner_account_id`"
            + ",`GRP`.`grp_status` AS `grp_status`"
            + ",`GrpStat`.`name` AS `grp_status_name`"
            + ",`GRP`.`memo` AS `memo`"
            + ",DATE_FORMAT(`GRP`.`updated`, '%Y/%m/%d %H:%i:%s') AS `updated`"
            + " FROM `m_group` `GRP`"
            +   " LEFT OUTER JOIN `g_kind` `GrpStat`"
            +       " ON `GrpStat`.`kbn` = 'GrpStat' AND `GrpStat`.`seq` <> 0 AND `GrpStat`.`code` = `GRP`.`grp_status`"
            )
})
public class SelectGroupData extends MGroup {

    //---------------------------------------------- const [public].
    /** 並び順 [最小]. */
    public static final int SORT_MIN = 1;
    /** 並び順 [最大]. */
    public static final int SORT_MAX = 8;



    //---------------------------------------------- properies [private].
    /** グループ状態名. */
    @Param
    private String grp_status_name;
    /** グループメンバー数. */
    @Param
    private String member_count;
    /** ルートフラグ. */
    @Param
    private String is_root;

    //-- setter / getter. --//
    /** グループ状態名 を取得します. */
    public String getGrp_status_name() { return grp_status_name; }
    /** グループ状態名 を設定します. */
    public void setGrp_status_name(String grp_status_name) { this.grp_status_name = grp_status_name; }
    /** グループメンバー数 を取得します. */
    public String getMember_count() { return member_count; }
    /** グループメンバー数 を設定します. */
    public void setMember_count(String member_count) { this.member_count = member_count; }
    /** ルートフラグ を取得します. */
    public String getIs_root() { return is_root; }
    /** ルートフラグ を設定します. */
    public void setIs_root(String is_root) { this.is_root = is_root; }

}
