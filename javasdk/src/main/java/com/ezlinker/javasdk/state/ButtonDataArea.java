package com.ezlinker.javasdk.state;

public class ButtonDataArea extends DataArea {
    private boolean state;

    public ButtonDataArea(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ButtonDataArea{" +
                "state='" + state + '\'' +
                '}';
    }
}
