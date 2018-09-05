package com.res_system.re_emp_manager.model.emp_info;

import com.res_system.commons.dao.entities.Sql;
import com.res_system.commons.dao.entities.Sqls;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.mvc.model.form.Param;
import com.res_system.re_emp_manager.commons.model.entities.SEmpInfo;

/**
 * 社員個人情報管理処理用 社員情報サブマスタ Entityクラス.
 * @author res.
 */
@Sqls({
    @Sql(name="find_list", sql = ""
            + "SELECT"
            + " `EMPHD`.`seq` AS `seq`"
            + ",`EMPHD`.`label` AS `label`"
            + ",`EMPHD`.`type` AS `type`"
            + ",`EMPHD`.`required` AS `required`"
            + ",`EMPHD`.`maxlength` AS `maxlength`"
            + ",`EMPHD`.`memo` AS `memo`"
            + ",`EMPHD`.`id` AS `emp_info_hd_id`"
            + ",`EMPIF`.`value` AS `value`"
            + " FROM `m_emp_info_hd` `EMPHD`"
            +   " LEFT OUTER JOIN `s_emp_info` `EMPIF`"
            +       " ON `EMPIF`.`emp_info_hd_id` = `EMPHD`.`id` AND `EMPIF`.`user_id` = ?"
            + " WHERE `EMPHD`.`status` = '0'"
            +   " AND `EMPHD`.`root_group_id` = ?"
            + " ORDER BY `seq`"
            )
    ,@Sql(name=SqlMaker.SQL_NAME_UPDATE, sql = ""
            + "--INSERT ("
            + " `user_id`"
            + ",`emp_info_hd_id`"
            + ",`value`"
            + ",`updated_id`"
            + ",`created_id`"
            + ") VALUES ("
            + " :user_id"
            + ",:emp_info_hd_id"
            + ",:value"
            + ",:updated_id"
            + ",:created_id"
            + ")"
            + " ON DUPLICATE KEY UPDATE"
            + " `value` = :value"
            + ",`updated_id` = :updated_id"
            + ",`updated` = CURRENT_TIMESTAMP"
            )
})
public class EmpInfoSEmpInfo extends SEmpInfo {

    //---------------------------------------------- properies [private].
    /** 連番. */
    @Param
    private String seq;
    /** ヘッダラベル. */
    @Param
    private String label;
    /** タイプ. */
    @Param
    private String type;
    /** 必須フラグ. */
    @Param
    private String required;
    /** 長さ. */
    @Param
    private String maxlength;
    /** 状態. */
    @Param
    private String status;
    /** 備考. */
    @Param
    private String memo;

    //-- setter / getter. --//
    /** 連番 を取得します. */
    public String getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(String seq) { this.seq = seq; }
    /** ヘッダラベル を取得します. */
    public String getLabel() { return label; }
    /** ヘッダラベル を設定します. */
    public void setLabel(String label) { this.label = label; }
    /** タイプ を取得します. */
    public String getType() { return type; }
    /** タイプ を設定します. */
    public void setType(String type) { this.type = type; }
    /** 必須フラグ を取得します. */
    public String getRequired() { return required; }
    /** 必須フラグ を設定します. */
    public void setRequired(String required) { this.required = required; }
    /** 長さ を取得します. */
    public String getMaxlength() { return maxlength; }
    /** 長さ を設定します. */
    public void setMaxlength(String maxlength) { this.maxlength = maxlength; }
    /** 状態 を取得します. */
    public String getStatus() { return status; }
    /** 状態 を設定します. */
    public void setStatus(String status) { this.status = status; }
    /** 備考 を取得します. */
    public String getMemo() { return memo; }
    /** 備考 を設定します. */
    public void setMemo(String memo) { this.memo = memo; }

}