package com.ezlinker.javasdk.view;

import com.ezlinker.javasdk.base.EZView;
import com.ezlinker.javasdk.state.ChartDataArea;
import com.ezlinker.javasdk.state.DataArea;

public final class EZChart extends EZView<ChartDataArea> {
    public EZChart(String token) {
        super(token);
    }

    @Override
    protected void push(ChartDataArea chartDataArea) {

    }

    @Override
    public Class<? extends DataArea> getDataArea() {
        return ChartDataArea.class;
    }
}
