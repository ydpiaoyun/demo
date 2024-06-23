#FROM openjdk:8-jdk-alpine 或 openjdk:8-jre-alpine
# Openjdk8 停止发布，eclipse-temurin 是openjdk的延伸
FROM eclipse-temurin:8-jre
#维护者信息
MAINTAINER zxy <ydpiaoyun@gmail.com>

ENV TZ=Asia/Shanghai  JAVA_OPTS="-Xms256m -Xmx256m"
WORKDIR apps
COPY target/*.jar app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/apps/demo.jar"]
CMD  java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /apps/app.jar
# 对外端口
EXPOSE 9000
#dockerfile中增加健康检查，interval表示每个5分钟检查一次，timeout每次超过5秒钟认定为失败，retries失败后轮询3次认定为容器不健康，考虑重启。
#HEALTHCHECK --interval=60s --timeout=5s --retries=6 \
#  CMD curl -f http://localhost:8989/health || exit 1