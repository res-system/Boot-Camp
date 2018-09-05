package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.mvc.model.form.Param;


import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;

/**
 * 住所マスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/06. 
 */
@Table(name="g_addr", as="ADD")
public class GAddr implements IEntity {

    //---------------------------------------------- properies [private].
    /** 住所ID. */
    @Param
    @Key
    @Column
    private String id;
    /** 郵便番号. */
    @Param
    @Column
    private String postal_code;
    /** 住所1. */
    @Param
    @Column
    private String addr1;
    /** 住所2. */
    @Param
    @Column
    private String addr2;
    /** 最寄り駅. */
    @Param
    @Column
    private String nearest_station;
    /** 更新者ID. */
    @Param
    @Column
    private String updated_id;
    /** 更新日時. */
    @Param
    @Column(isInsert=false)
    private String updated;
    /** 作成者ID. */
    @Param
    @Column
    private String created_id;
    /** 作成日時. */
    @Param
    @Column(isInsert=false,isUpdate=false)
    private String created;

    //-- setter / getter. --//
    /** 住所ID を取得します. */
    public String getId() { return id; }
    /** 住所ID を設定します. */
    public void setId(String id) { this.id = id; }
    /** 郵便番号 を取得します. */
    public String getPostal_code() { return postal_code; }
    /** 郵便番号 を設定します. */
    public void setPostal_code(String postal_code) { this.postal_code = postal_code; }
    /** 住所1 を取得します. */
    public String getAddr1() { return addr1; }
    /** 住所1 を設定します. */
    public void setAddr1(String addr1) { this.addr1 = addr1; }
    /** 住所2 を取得します. */
    public String getAddr2() { return addr2; }
    /** 住所2 を設定します. */
    public void setAddr2(String addr2) { this.addr2 = addr2; }
    /** 最寄り駅 を取得します. */
    public String getNearest_station() { return nearest_station; }
    /** 最寄り駅 を設定します. */
    public void setNearest_station(String nearest_station) { this.nearest_station = nearest_station; }
    /** 更新者ID を取得します. */
    public String getUpdated_id() { return updated_id; }
    /** 更新者ID を設定します. */
    public void setUpdated_id(String updated_id) { this.updated_id = updated_id; }
    /** 更新日時 を取得します. */
    public String getUpdated() { return updated; }
    /** 更新日時 を設定します. */
    public void setUpdated(String updated) { this.updated = updated; }
    /** 作成者ID を取得します. */
    public String getCreated_id() { return created_id; }
    /** 作成者ID を設定します. */
    public void setCreated_id(String created_id) { this.created_id = created_id; }
    /** 作成日時 を取得します. */
    public String getCreated() { return created; }
    /** 作成日時 を設定します. */
    public void setCreated(String created) { this.created = created; }

}
