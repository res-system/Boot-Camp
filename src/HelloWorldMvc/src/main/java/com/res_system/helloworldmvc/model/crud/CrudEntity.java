package com.res_system.helloworldmvc.model.crud;

import com.res_system.commons.dao.entities.Column;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.entities.Key;
import com.res_system.commons.dao.entities.Table;
import com.res_system.commons.mvc.model.form.Param;

/**
 * <pre>
 * Crud用Entityクラス(テスト用).
 * </pre>
 * @author res.
 */
@Table(name="test_crud_table", as="A")
public class CrudEntity implements IEntity {

    //---------------------------------------------- properies [private].
    @Param
    @Key
    @Column(isInsert=false)
    private String id;
    @Param
    @Column
    private String code;
    @Param
    @Column
    private String name;
    @Param
    @Column
    private String memo;
    @Param
    @Column
    private String check;
    @Param
    @Column
    private String radio;
    @Param
    @Column
    private String select;
    @Param
    @Column(insertValue="now()", updateValue="now()")
    private String created;

    //-- setter / getter. --//
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
    public String getCheck() { return check;}
    public void setCheck(String check) { this.check = check; }
    public String getRadio() { return radio; }
    public void setRadio(String radio) { this.radio = radio; }
    public String getSelect() { return select; }
    public void setSelect(String select) { this.select = select; }
    public String getCreated() { return created; }
    public void setCreated(String createdDate) { this.created = createdDate; }

}
