package com.res_system.mvc_skeleton.model.top;

import com.res_system.commons.mvc.model.form.Param;

public class TopForm {

    //---------------------------------------------- properies [private].
    @Param
    private String code;
    @Param
    private String name;

    //-- setter / getter. --//
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}
