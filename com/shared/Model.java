package com.shared;

import java.io.Serializable;
import java.net.InetAddress;

public class Model implements Serializable {
    String type;
    String op;
    InetAddress ip;
    int _id;

    Model(InetAddress ip,String type,String op,int _id){
        this.type = type;
        this.op = op;
        this.ip = ip;
        this._id = _id;
    }

    public int getId() {
        return this._id;
    }
    public String getType() {
        return this.type;
    }
    public String getOp() {
        return this.op;
    }
    public InetAddress getIp(){
        return this.ip;
    }

    public void setId(int id) {
        this._id = _id;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setOp(String op) {
        this.op = op;
    }
    public void setIp(InetAddress ip){
        this.ip = ip;
    }
}
