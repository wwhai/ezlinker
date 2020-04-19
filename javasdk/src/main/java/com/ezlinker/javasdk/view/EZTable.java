package com.ezlinker.javasdk.view;

import com.ezlinker.javasdk.base.EZView;
import com.ezlinker.javasdk.state.ChartDataArea;
import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.TableDataArea;

public final class EZTable extends EZView<TableDataArea> {
    public EZTable(String token) {
        super(token);
    }

    @Override
    protected void push(TableDataArea tableDataArea) {

    }

    @Override
    public Class<? extends DataArea> getDataArea() {
        return ChartDataArea.class;
    }
}
