package com.res_system.re_emp_manager.commons.model.entities;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * メニュー関連マスタ Entityクラス.
 * @author res.
 *  generated at 2018/08/13. 
 */
@Table(name="r_menu", as="MNU")
public class RMenu implements IEntity {

    //---------------------------------------------- properies [private].
    /** メニュー番号. */
    @Param
    @Key
    @Column
    private String no;
    /** 連番. */
    @Param
    @Key
    @Column
    private String seq;
    /** レベル. */
    @Param
    @Column
    private String level;
    /** 許可ID. */
    @Param
    @Column
    private String permission_id;
    /** タイトル. */
    @Param
    @Column
    private String title;
    /** 説明. */
    @Param
    @Column
    private String description;
    /** アイコン. */
    @Param
    @Column
    private String icon;
    /** URL. */
    @Param
    @Column
    private String url;
    /** 状態. */
    @Param
    @Column
    private String status;
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
    /** メニュー番号 を取得します. */
    public String getNo() { return no; }
    /** メニュー番号 を設定します. */
    public void setNo(String no) { this.no = no; }
    /** 連番 を取得します. */
    public String getSeq() { return seq; }
    /** 連番 を設定します. */
    public void setSeq(String seq) { this.seq = seq; }
    /** レベル を取得します. */
    public String getLevel() { return level; }
    /** レベル を設定します. */
    public void setLevel(String level) { this.level = level; }
    /** 許可ID を取得します. */
    public String getPermission_id() { return permission_id; }
    /** 許可ID を設定します. */
    public void setPermission_id(String permission_id) { this.permission_id = permission_id; }
    /** タイトル を取得します. */
    public String getTitle() { return title; }
    /** タイトル を設定します. */
    public void setTitle(String title) { this.title = title; }
    /** 説明 を取得します. */
    public String getDescription() { return description; }
    /** 説明 を設定します. */
    public void setDescription(String description) { this.description = description; }
    /** アイコン を取得します. */
    public String getIcon() { return icon; }
    /** アイコン を設定します. */
    public void setIcon(String icon) { this.icon = icon; }
    /** URL を取得します. */
    public String getUrl() { return url; }
    /** URL を設定します. */
    public void setUrl(String url) { this.url = url; }
    /** 状態 を取得します. */
    public String getStatus() { return status; }
    /** 状態 を設定します. */
    public void setStatus(String status) { this.status = status; }
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
