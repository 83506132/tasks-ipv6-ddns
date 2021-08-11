#除非有需求不然真不建议部署到docker里面因为有虚拟ip这个概念获取不到真实ip
FROM java:11
MAINTAINER 赵Muse <83506132@qq.com>
RUN mkdir -p  /usr/local/ddns/logs
WORKDIR /usr/local/ddns/
VOLUME /usr/local/ddns/logs
ADD start.sh start.sh
ADD ./target/applicationDDNS.jar applicationDDNS.jar
ENTRYPOINT ["bash","./start.sh"]