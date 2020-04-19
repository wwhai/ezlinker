package com.ezlinker.javasdk.component;

import com.ezlinker.javasdk.base.ComponentListener;
import com.ezlinker.javasdk.base.EZComponent;
import com.ezlinker.javasdk.base.ProtocolSupport;
import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.SwitchDataArea;

public final class EZSwitch extends EZComponent<SwitchDataArea> {


    public EZSwitch(String token, ProtocolSupport protocolSupport) {
        super(token, protocolSupport);
    }


    @Override
    public void addListener(ComponentListener<SwitchDataArea> componentListener) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Class<? extends DataArea> getDataArea() {
        return SwitchDataArea.class;
    }
}
