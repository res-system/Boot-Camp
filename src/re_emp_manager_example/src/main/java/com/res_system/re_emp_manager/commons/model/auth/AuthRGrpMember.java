package com.res_system.re_emp_manager.commons.model.auth;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.RGrpMember;

/**
 * 認証処理用 グループメンバー関連マスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT "
            + " `GRP`.`id` AS `id`"
            + ",`RGRP`.`id` AS `root_group_id`"
            + ",`RGRP`.`name` AS `root_group_name`"
            + ",`GRP`.`id` AS `group_id`"
            + ",`GRP`.`name` AS `group_name`"
            + ",`USR`.`id` AS `user_id`"
            + ",`USR`.`name` AS `user_name`"
            + ",`AUT`.`id` AS `authority_id`"
            + ",`AUT`.`name` AS `authority_name`"
            + ",DATE_FORMAT(`GRP`.`created`, '%Y/%m/%d %H:%i:%s') AS `created`"
            + ",(CASE WHEN `GRP`.`root_group_id` = `GRP`.`id` THEN '1'"
            +       " ELSE '0' END) AS `is_root`"
            // -- キーワード検索項目 start. --//
            + ",CONCAT(IFNULL(`RGRP`.`name`,'')"
            +    ",' ',IFNULL(`GRP`.`name`,'')"
            +    ",' ',IFNULL(`USR`.`name`,'')"
            +    ",' ',IFNULL(`AUT`.`name`,'')"
            +    ",' ',IFNULL(DATE_FORMAT(`GRP`.`created`, '%Y/%m/%d %H:%i:%s'),'')"
            +    ") AS `keyword`"
            // -- キーワード検索項目 end  .--//
            + " FROM `m_user` `USR`"
            +   " INNER JOIN `r_grp_member` `GRPMB` ON `GRPMB`.`user_id` = `USR`.`id`"
            +   " LEFT OUTER JOIN `m_authority` `AUT` ON `AUT`.`id` = `GRPMB`.`authority_id`"
            +   " INNER JOIN `m_group` `GRP`"
            +       " ON `GRP`.`id` = `GRPMB`.`group_id` AND `GRP`.`grp_status` IN('0', '1')"
            +   " INNER JOIN `s_grp_account` `GRPAC`"
            +       " ON `GRPAC`.`user_id` = `USR`.`id` AND `GRPAC`.`gpac_status` IN('0', '1')"
            +   " INNER JOIN `m_group` `RGRP`"
            +       " ON `RGRP`.`id` = `GRPAC`.`root_group_id` AND `GRP`.`grp_status` IN('0', '1')"
            + " WHERE `USR`.`id` = ?"
            )
    ,@Sql(name="find_list_order", sql = "ORDER BY `created` DESC ")
    ,@Sql(name="find_list_order_1", sql = "ORDER BY `group_name` ASC")
    ,@Sql(name="find_list_order_2", sql = "ORDER BY `group_name` DESC")
    ,@Sql(name="find_list_order_3", sql = "ORDER BY `authority_name` ASC ")
    ,@Sql(name="find_list_order_4", sql = "ORDER BY `authority_name` DESC ")
    ,@Sql(name="find_list_order_5", sql = "ORDER BY `created` ASC ")
    ,@Sql(name="find_list_order_6", sql = "ORDER BY `created` DESC ")
    ,@Sql(name="check_group_id", sql = "WHERE `group_id` = ?")
})
public class AuthRGrpMember extends RGrpMember {

    //---------------------------------------------- const [public].
    /** 並び順 [最小]. */
    public static final int SORT_MIN = 1;
    /** 並び順 [最大]. */
    public static final int SORT_MAX = 10;


    //---------------------------------------------- properies [private].
    /** グループID. */
    @Param
    private String id;
    /** ルートグループID. */
    @Param
    private String root_group_id;
    /** ルートグループ名. */
    @Param
    private String root_group_name;
    /** グループ名. */
    @Param
    private String group_name;
    /** ユーザー名. */
    @Param
    private String user_name;
    /** 権限名. */
    @Param
    private String authority_name;

    /** ルートフラグ. */
    @Param
    private String is_root;


    //-- setter / getter. --//
    /** グループID を取得します. */
    public String getId() { return id; }
    /** グループID を設定します. */
    public void setId(String id) { this.id = id; }
    /** ルートグループID を取得します. */
    public String getRoot_group_id() { return root_group_id; }
    /** ルートグループID を設定します. */
    public void setRoot_group_id(String root_group_id) { this.root_group_id = root_group_id; }
    /** ルートグループ名 を取得します. */
    public String getRoot_group_name() { return root_group_name; }
    /** ルートグループ名 を設定します. */
    public void setRoot_group_name(String root_group_name) { this.root_group_name = root_group_name; }
    /** グループ名 を取得します. */
    public String getGroup_name() { return group_name; }
    /** グループ名 を設定します. */
    public void setGroup_name(String group_name) { this.group_name = group_name; }
    /** ユーザー名 を取得します. */
    public String getUser_name() { return user_name; }
    /** ユーザー名 を設定します. */
    public void setUser_name(String user_name) { this.user_name = user_name; }
    /** 権限名 を取得します. */
    public String getAuthority_name() { return authority_name; }
    /** 権限名 を設定します. */
    public void setAuthority_name(String authority_name) { this.authority_name = authority_name; }

    /** ルートフラグ を取得します. */
    public String getIs_root() { return is_root; }
    /** ルートフラグ を設定します. */
    public void setIs_root(String is_root) { this.is_root = is_root; }

}
