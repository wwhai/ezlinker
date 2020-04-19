package com.ezlinker.javasdk.component;

import com.ezlinker.javasdk.base.ComponentListener;
import com.ezlinker.javasdk.base.EZComponent;
import com.ezlinker.javasdk.base.ProtocolSupport;
import com.ezlinker.javasdk.state.ButtonGroupDataArea;
import com.ezlinker.javasdk.state.DataArea;

public final class EZButtonGroup extends EZComponent<ButtonGroupDataArea> {


    public EZButtonGroup(String token, ProtocolSupport protocolSupport) {
        super(token, protocolSupport);
    }


    @Override
    public void addListener(ComponentListener<ButtonGroupDataArea> componentListener) {

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
        return ButtonGroupDataArea.class;
    }
}
