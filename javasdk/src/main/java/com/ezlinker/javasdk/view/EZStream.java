package com.ezlinker.javasdk.view;

import com.ezlinker.javasdk.base.EZView;
import com.ezlinker.javasdk.state.ChartDataArea;
import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.StreamDataArea;

public final class EZStream extends EZView<StreamDataArea> {
    public EZStream(String token) {
        super(token);
    }

    @Override
    protected void push(StreamDataArea streamDataArea) {

    }

    @Override
    public Class<? extends DataArea> getDataArea() {
        return ChartDataArea.class;
    }
}
