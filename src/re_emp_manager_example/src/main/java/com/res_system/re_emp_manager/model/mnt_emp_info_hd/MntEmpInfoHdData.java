package com.res_system.re_emp_manager.model.mnt_emp_info_hd;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.MEmpInfoHd;

/**
 * 社員情報ヘッダーマスタメンテナンス処理用 社員情報ヘッダーマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "--SELECT"
            + ",`InfType`.`name` AS `type_name`"
            + ",`ReqFlg`.`name` AS `required_name`"
            + ",`Stat`.`name` AS `status_name`"
            // -- キーワード検索項目 start. --//
            + ",CONCAT(IFNULL(`EMPHD`.`label`,'')"
            +    ",' ',IFNULL(`EMPHD`.`memo`,'')"
            +    ",' ',IFNULL(`InfType`.`name`,'')"
            +    ",' ',IFNULL(`ReqFlg`.`name`,'')"
            +    ",' ',IFNULL(`Stat`.`name`,'')"
            +    ") AS `keyword`"
            // -- キーワード検索項目 end  .--//
            + " --FROM"
            +   " LEFT OUTER JOIN `g_kind` `InfType`"
            +       " ON `InfType`.`kbn` = 'InfType' AND `InfType`.`seq` <> 0 AND `InfType`.`code` = `EMPHD`.`type`"
            +   " LEFT OUTER JOIN `g_kind` `ReqFlg`"
            +       " ON `ReqFlg`.`kbn` = 'ReqFlg' AND `ReqFlg`.`seq` <> 0 AND `ReqFlg`.`code` = `EMPHD`.`required`"
            +   " LEFT OUTER JOIN `g_kind` `Stat`"
            +       " ON `Stat`.`kbn` = 'Stat' AND `Stat`.`seq` <> 0 AND `Stat`.`code` = `EMPHD`.`status`"
            + " WHERE `EMPHD`.`root_group_id` = ?"
            )
    ,@Sql(name="find_list_order", sql = "ORDER BY `seq` ")
    ,@Sql(name="find_list_order_1", sql = "ORDER BY `seq` ASC ")
    ,@Sql(name="find_list_order_2", sql = "ORDER BY `seq` DESC ")
    ,@Sql(name="find_list_order_3", sql = "ORDER BY `label` ASC ")
    ,@Sql(name="find_list_order_4", sql = "ORDER BY `label` DESC ")
    ,@Sql(name="find_list_order_5", sql = "ORDER BY `type_name` ASC ")
    ,@Sql(name="find_list_order_6", sql = "ORDER BY `type_name` DESC ")
    ,@Sql(name="find_list_order_7", sql = "ORDER BY `required` ASC ")
    ,@Sql(name="find_list_order_8", sql = "ORDER BY `required` DESC ")
    ,@Sql(name="find_list_order_9", sql = "ORDER BY `maxlength` ASC ")
    ,@Sql(name="find_list_order_10", sql = "ORDER BY `maxlength` DESC ")
    ,@Sql(name="find_list_order_11", sql = "ORDER BY `status` ASC ")
    ,@Sql(name="find_list_order_12", sql = "ORDER BY `status` DESC ")
    ,@Sql(name="find_list_order_13", sql = "ORDER BY `memo` ASC ")
    ,@Sql(name="find_list_order_14", sql = "ORDER BY `memo` DESC ")
    ,@Sql(name="find_status", sql = "`status` = '0'")
    ,@Sql(name="check_count", sql = ""
            + "SELECT COUNT(`EMPHD`.`id`) AS `count`"
            + " --FROM"
            + " WHERE `EMPHD`.`root_group_id` = ?"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_SELECT_BY_KEY, sql = ""
            + "--SELECT"
            + ",`InfType`.`name` AS `type_name`"
            + ",`ReqFlg`.`name` AS `required_name`"
            + ",`Stat`.`name` AS `status_name`"
            // -- キーワード検索項目 start. --//
            + ",CONCAT(IFNULL(`EMPHD`.`label`,'')"
            +    ",' ',IFNULL(`EMPHD`.`memo`,'')"
            +    ",' ',IFNULL(`InfType`.`name`,'')"
            +    ",' ',IFNULL(`ReqFlg`.`name`,'')"
            +    ",' ',IFNULL(`Stat`.`name`,'')"
            +    ") AS `keyword`"
            // -- キーワード検索項目 end  .--//
            + " --FROM"
            +   " LEFT OUTER JOIN `g_kind` `InfType`"
            +       " ON `InfType`.`kbn` = 'InfType' AND `InfType`.`seq` <> 0 AND `InfType`.`code` = `EMPHD`.`type`"
            +   " LEFT OUTER JOIN `g_kind` `ReqFlg`"
            +       " ON `ReqFlg`.`kbn` = 'ReqFlg' AND `ReqFlg`.`seq` <> 0 AND `ReqFlg`.`code` = `EMPHD`.`required`"
            +   " LEFT OUTER JOIN `g_kind` `Stat`"
            +       " ON `Stat`.`kbn` = 'Stat' AND `Stat`.`seq` <> 0 AND `Stat`.`code` = `EMPHD`.`status`"
            + " --WHERE_WT"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--UPDATE"
            + " `seq` = :seq "
            + ",`label` = :label "
            + ",`type` = :type "
            + ",`required` = :required "
            + ",`maxlength` = :maxlength "
            + ",`status` = :status "
            + ",`memo` = :memo "
            + ",`updated_id` = :updated_id "
            + ",`updated` = CURRENT_TIMESTAMP "
            + " --WHERE"
            )
    ,@Sql(name="check_seq", sql = ""
            + "SELECT COUNT(`EMPHD`.`seq`) AS `count`"
            + " --FROM"
            + " WHERE `EMPHD`.`root_group_id` = ?"
            +   " AND `EMPHD`.`seq` = ?"
            +   " AND `EMPHD`.`id` <> ?"
            )
    ,@Sql(name="get_max_seq", sql = ""
            + "SELECT IFNULL((MAX(`EMPHD`.`seq`) + 1), 1) AS `max_seq`"
            + " --FROM"
            + " WHERE `EMPHD`.`root_group_id` = ?"
            )
})
public class MntEmpInfoHdData extends MEmpInfoHd {

    //---------------------------------------------- const [public].
    /** 並び順 [最小]. */
    public static final int SORT_MIN = 1;
    /** 並び順 [最大]. */
    public static final int SORT_MAX = 14;



    //---------------------------------------------- properies [private].
    /** タイプ名. */
    @Param
    private String type_name;
    /** 必須フラグ名. */
    @Param
    private String required_name;
    /** 状態名. */
    @Param
    private String status_name;

    //-- setter / getter. --//
    /** タイプ名 を取得します. */
    public String getType_name() { return type_name; }
    /** タイプ名 を設定します. */
    public void setType_name(String type_name) { this.type_name = type_name; }
    /** 必須フラグ名 を取得します. */
    public String getRequired_name() { return required_name; }
    /** 必須フラグ名 を設定します. */
    public void setRequired_name(String required_name) { this.required_name = required_name; }
    /** 状態名 を取得します. */
    public String getStatus_name() { return status_name; }
    /** 状態名 を設定します. */
    public void setStatus_name(String status_name) { this.status_name = status_name; }

}
