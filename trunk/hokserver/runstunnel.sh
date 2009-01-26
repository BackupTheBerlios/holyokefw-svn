#!/bin/sh
#

CONF=$HOME/.hokserver/stunnel.conf
PIDFILE=$HOME/.hokserver/stunnel.pid
KEYS=$HOME/.hokserver/data/keys

echo "pid = $PIDFILE" >$CONF
cat >>$CONF <<EOF
foreground = yes

[postgresql]
accept = 5433
connect = 5432
; Contains the set of client certificates
CApath = $KEYS/server/ssl/capath
; Contains the server's self-signed certificate and private key
cert = $KEYS/server/ssl/server.pem
verify = 3
EOF

stunnel $CONF
