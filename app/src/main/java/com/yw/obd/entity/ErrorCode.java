package com.yw.obd.entity;

/**
 * Created by apollo on 2017/8/30.
 */

public class ErrorCode {

    /**
     * id : 1
     * codeNum : P0001
     * codename : 燃油量调节器控制电路/开路
     */

    private int id;
    private String codeNum;
    private String codename;

    @Override
    public String toString() {
        return "ErrorCode{" +
                "id=" + id +
                ", codeNum='" + codeNum + '\'' +
                ", codename='" + codename + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }
}
