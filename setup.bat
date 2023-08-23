cd /d %~dp0
set PATH=%PATH%;src/bin/libimobile device;src/bin/platform-tools
java -jar PhoneInfoTool-1.0-SNAPSHOT-jar-with-dependencies.jar >out.log 2>&1 &

