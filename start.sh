if [ ! -n "${JAVA_HOME}" ]; then
  nohup java -jar applicationDDNS.jar >/dev/null 2>&1 &
fi
