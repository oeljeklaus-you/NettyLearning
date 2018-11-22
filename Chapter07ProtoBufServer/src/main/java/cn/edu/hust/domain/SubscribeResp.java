package cn.edu.hust.domain;

import java.io.Serializable;

public class SubscribeResp implements Serializable{
    private int subReqID;

    private int respID;

    private String desc;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespID() {
        return respID;
    }

    public void setRespID(int respID) {
        this.respID = respID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "subReqID=" + subReqID +
                ", respID=" + respID +
                ", desc='" + desc + '\'' +
                '}';
    }
}
