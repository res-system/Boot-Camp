package com.res_system.mvc_skeleton.model.top;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TopModel {

    public void init(TopForm form) {
        form.setCode("");
        form.setName("nothing");
    }

    public void doAction(TopForm form) {
        if (form.getCode() != null && form.getCode().length() > 0) {
            form.setName("something[" + form.getCode() + "]");
        } else {
            form.setName("nothing");
        }
    }

}
