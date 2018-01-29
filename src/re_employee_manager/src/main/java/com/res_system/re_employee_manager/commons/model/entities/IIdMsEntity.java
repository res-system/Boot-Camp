package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.IEntity;

/**
 * メインマスタEntityインターフェース.
 * @author res.
 */
public interface IIdMsEntity extends IEntity {

    /** ID を取得します. */
    Long getId();
    /** ID を設定します. */
    void setId(Long id);
    /** 更新者ID を取得します. */
    Long getUpdated_id();
    /** 更新者ID を設定します. */
    void setUpdated_id(Long updated_id);
    /** 作成者ID を取得します. */
    Long getCreated_id();
    /** 作成者ID を設定します. */
    void setCreated_id(Long created_id);

}
