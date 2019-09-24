package com.res_system.commons.mvc.model.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestForm {

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

    @Param
    List<String> checks;

    @DataParam
    TestFormData data;
    @ListParam(TestFormData.class)
    List<TestFormData> list;

    @Param
    Integer ignoreType01;
    @Param
    Long ignoreType02;
    @Param
    BigDecimal ignoreType03;
    @Param
    int ignoreType04;
    @Param
    long ignoreType05;

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

    public List<String> getChecks() { return checks; }
    public void setChecks(List<String> checks) { this.checks = checks; }

    public TestFormData getData() { return data; }
    public void setData(TestFormData data) { this.data = data; }
    public List<TestFormData> getList() { return list; }
    public void setList(List<TestFormData> list) { this.list = list; }


    public Integer getIgnoreType01() { return ignoreType01; }
    public void setIgnoreType01(Integer ignoreType01) { this.ignoreType01 = ignoreType01; }
    public Long getIgnoreType02() { return ignoreType02; }
    public void setIgnoreType02(Long ignoreType02) { this.ignoreType02 = ignoreType02; }
    public BigDecimal getIgnoreType03() { return ignoreType03; }
    public void setIgnoreType03(BigDecimal ignoreType03) { this.ignoreType03 = ignoreType03; }
    public int getIgnoreType04() { return ignoreType04; }
    public void setIgnoreType04(int ignoreType04) { this.ignoreType04 = ignoreType04; }
    public long getIgnoreType05() { return ignoreType05; }
    public void setIgnoreType05(long ignoreType05) { this.ignoreType05 = ignoreType05; }


    public TestForm() {
        super();
        checks = new ArrayList<String>();
        data = new TestFormData();
        list = new ArrayList<TestFormData>();
    }


}
