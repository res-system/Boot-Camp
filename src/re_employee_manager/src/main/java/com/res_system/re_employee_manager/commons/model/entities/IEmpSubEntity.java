package com.res_system.re_employee_manager.commons.model.entities;

import com.res_system.commons.dao.entities.IEntity;

/**
 * グループサブマスタEntityインターフェース.
 * @author res.
 */
public interface IEmpSubEntity extends IEntity {

    /** 社員ID を取得します. */
    Long getEmployee_id();
    /** 社員ID を設定します. */
    void setEmployee_id(Long employee_id);
    /** 連番 を取得します. */
    Integer getSeq();
    /** 連番 を設定します. */
    void setSeq(Integer seq);
    /** 更新者ID を取得します. */
    Long getUpdated_id();
    /** 更新者ID を設定します. */
    void setUpdated_id(Long updated_id);
    /** 作成者ID を取得します. */
    Long getCreated_id();
    /** 作成者ID を設定します. */
    void setCreated_id(Long created_id);

}
