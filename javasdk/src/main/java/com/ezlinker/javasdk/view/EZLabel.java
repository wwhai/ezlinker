package com.ezlinker.javasdk.view;

import com.ezlinker.javasdk.base.EZView;
import com.ezlinker.javasdk.state.ChartDataArea;
import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.LabelDataArea;

public final class EZLabel extends EZView<LabelDataArea> {

    public EZLabel(String token) {
        super(token);
    }

    @Override
    protected void push(LabelDataArea labelDataArea) {

    }

    @Override
    public Class<? extends DataArea> getDataArea() {
        return ChartDataArea.class;
    }
}
