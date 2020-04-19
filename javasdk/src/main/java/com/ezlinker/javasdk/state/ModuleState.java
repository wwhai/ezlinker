package com.ezlinker.javasdk.state;

public class ModuleState {
    private String token;
    private DataArea area;

    public ModuleState(String token, DataArea area) {
        this.token = token;
        this.area = area;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DataArea getArea() {
        return area;
    }

    public void setArea(DataArea area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "ModuleState{" +
                "token='" + token + '\'' +
                ", area=" + area +
                '}';
    }
}
