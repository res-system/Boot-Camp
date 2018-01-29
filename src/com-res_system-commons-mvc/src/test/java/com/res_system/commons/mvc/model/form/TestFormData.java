package com.res_system.commons.mvc.model.form;

public class TestFormData {

    @Param
    String id;
    @Param
    String code;
    @Param
    String name;
    @Param
    String memo;
    @Param
    String nothing;

    String dummy;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
    public String getNothing() { return nothing; }
    public void setNothing(String nothing) { this.nothing = nothing; }

    public String getDummy() { return dummy; }
    public void setDummy(String dummy) { this.dummy = dummy; }


}
