#!/bin/sh
epmd -daemon
/emqx/bin/emqx start >> /emqx.log
tail -f /dev/null