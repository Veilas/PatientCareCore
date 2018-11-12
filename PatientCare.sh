#! /bin/sh
# /etc/init.d/example

case "$1" in
 start)
   echo "Starting example"
   # run application you want to start
   python /usr/local/sbin/kreveti.py &
   ;;
 stop)
   echo "Stopping example"
   # kill application you want to stop
   killall python
   ;;
 *)
   echo "Usage: /etc/init.d/example{start|stop}"
   exit 1
   ;;
esac

exit 0
