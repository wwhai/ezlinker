package com.ezlinker.javasdk.component;

import com.ezlinker.javasdk.base.EZComponent;
import com.ezlinker.javasdk.base.ProtocolSupport;
import com.ezlinker.javasdk.state.ButtonDataArea;
import com.ezlinker.javasdk.state.DataArea;

public final class EZButton extends EZComponent<ButtonDataArea> {


    public EZButton(String token, ProtocolSupport protocolSupport) {
        super(token, protocolSupport);
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
        return ButtonDataArea.class;
    }
}
