# coding=utf-8
import paho.mqtt.client as mqtt
import random
import time


# 连接成功回调
def on_connect(_client, _userdata, _flags, rc):
    print('Connected with result code ' + str(rc))
    client.subscribe('$EZLINKER/ad961d6b3a287cb435346e42326ab2bf/s2c')


# 消息接收回调
def on_message(_client, _userdata, msg):
    print(msg.topic + " " + str(msg.payload))


client = mqtt.Client("ad961d6b3a287cb435346e42326ab2bf")
client.username_pw_set("6539d425865776a5839d0c3edad990f1", "23a1acb9806572ae08db0a960a908b18")

# 指定回调函数
client.on_connect = on_connect
client.on_message = on_message

# 建立连接
client.connect('127.0.0.1', 1883, 60)
# 发布消息
A = str(random.randint(100, 200))
time.sleep(1)
B = str(random.randint(99, 300))
time.sleep(1)
C = str(random.randint(1, 40))
time.sleep(1)
print(str('C,#A,#B,#C'.replace("#A", A).replace("#B", B).replace("#C", C)))
client.publish('$EZLINKER/ad961d6b3a287cb435346e42326ab2bf/status',
               payload=str('C,#A,#B,#C'.replace("#A", A).replace("#B", B).replace("#C", C)), qos=0)

client.loop_forever()
