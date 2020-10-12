import redis

client = redis.Redis(host='106.75.57.171', port=56379, decode_responses=True, password="ezlinker_redis")
import time
import random

while True:
    time.sleep(1)
    client.hset("DEVICE_RUNNING_STATE:ad961d6b3a287cb435346e42326ab2bf", "A", random.randint(220, 240))

    time.sleep(1)
    client.hset("DEVICE_RUNNING_STATE:ad961d6b3a287cb435346e42326ab2bf", "B", random.randint(100, 200))

    time.sleep(1)
    client.hset("DEVICE_RUNNING_STATE:ad961d6b3a287cb435346e42326ab2bf", "C", random.randint(50, 100))
