package com.res_system.mvc_skeleton.model.top;

import javax.enterprise.context.RequestScoped;

import com.res_system.commons.util.ReUtil;

@RequestScoped
public class TopModel {

    public void init(TopForm form) {
        form.setCode("");
        form.setName("nothing");
    }

    public void doAction(TopForm form) {
        if (!ReUtil.isEmpty(form.getCode())) {
            form.setName("something[" + form.getCode() + "]");
        } else {
            form.setName("nothing");
        }
    }

}
