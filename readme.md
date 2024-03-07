# 猫不卷

## 项目 cat-upload-file2
文件上传

### git 代理
export https_proxy=http://127.0.0.1:7890

### 启动
nohup java -jar -Dspring.profiles.active=online cat-upload-file2-1.0-SNAPSHOT.jar > /dev/null 2>&1 &

### 停止
ps -ef |grep cat-upload-file2 | grep -v grep | awk '{print $2}'｜xargs kill -9

### 截图
x-oss-process=video/snapshot,t_1000,f_jpg,w_800,h_600,m_fast