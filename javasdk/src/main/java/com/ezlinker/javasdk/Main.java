package com.ezlinker.javasdk;

import com.ezlinker.javasdk.base.ComponentListener;
import com.ezlinker.javasdk.component.EZButton;
import com.ezlinker.javasdk.core.EZLinkerSDK;
import com.ezlinker.javasdk.protocol.MqttProtocolSupport;
import com.ezlinker.javasdk.protocol.config.MqttSupportConfig;
import com.ezlinker.javasdk.state.ButtonDataArea;
import com.ezlinker.javasdk.state.ModuleState;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        EZLinkerSDK ezLinkerSDK = new EZLinkerSDK();
        // username acf79a3c958220ab199f21a1ca8ce3c2
        // password 9dd5dbb9a0b98e18060651df880e5c6b
        // clientId 47585f995dfcfd4477c0d5be12891d8f
        // mqtt/out/3507380dbceaa9c094cd0c46e821c68b/s2c
        // mqtt/in/3507380dbceaa9c094cd0c46e821c68b/c2s
        // mqtt/in/3507380dbceaa9c094cd0c46e821c68b/state
        ezLinkerSDK.init("23rewt34q",
                new MqttProtocolSupport(
                        "localhost",
                        1883,
                        "47585f995dfcfd4477c0d5be12891d8f",
                        "acf79a3c958220ab199f21a1ca8ce3c2",
                        "9dd5dbb9a0b98e18060651df880e5c6b",
                        new MqttSupportConfig(
                                "mqtt/out/3507380dbceaa9c094cd0c46e821c68b/s2c",
                                "mqtt/out/3507380dbceaa9c094cd0c46e821c68b/c2s",
                                "mqtt/out/3507380dbceaa9c094cd0c46e821c68b/state")));
        ezLinkerSDK.start();
        EZButton ezButton1 = (EZButton) ezLinkerSDK.register("button-token-001", EZButton.class);
        ezButton1.addListener(new ComponentListener<>() {
            @Override
            public ButtonDataArea onData(ButtonDataArea buttonDataArea) throws MqttException {
                logger.debug("控件响应:" + buttonDataArea);
                // 组件更新状态
                // 假如说状态从开变成关
                ModuleState moduleState = new ModuleState(ezButton1.getToken(), new ButtonDataArea(false));
                return (ButtonDataArea) EZLinkerSDK.getComponentManager().get(ezButton1.getToken()).synchronizeState(moduleState);
            }

            @Override
            public void onError(String error) {

            }
        });

//        EZButton ezButton2 = (EZButton) ezLinkerSDK.register("ewrfew2", EZButton.class);
//        EZButtonGroup ezButtonGroup1 = (EZButtonGroup) ezLinkerSDK.register("ewrfe2324rd1", EZButtonGroup.class);
//        EZButtonGroup ezButtonGroup2 = (EZButtonGroup) ezLinkerSDK.register("ewrfe2324rd2", EZButtonGroup.class);
//        EZLabel ezLabel1 = (EZLabel) ezLinkerSDK.register("ewrfe2324rd1", EZLabel.class);
//        EZLabel ezLabel2 = (EZLabel) ezLinkerSDK.register("ewrfe2324rd2", EZLabel.class);

    }
}
