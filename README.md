# MyQQ
## 基于安卓的仿QQ及时聊天程序<br>
## 安卓客户端基于android studio 开发。<br>内部图片资源很大一部分是反编译手机QQapp 获得的。
 ## 服务器端技术路线<br>
聊天系统服务器基于Java Socket网络编程和并发编程、多线程技术、jdbc实现。<br>通过哈希表（hashmap）存储来自每一个向客户端的发送线程和消息监听进程，方便查询和管理。发送给好友的消息是以消息对列的形式发送出去，而对于登录，注册，查询，更改用户信息的对及时性要求高的消息则直接发送回馈消息不经过消息对列那一步。<br>
服务器端线程。发送消息类为自己定义的TranObject类，该类实现了Serializable接口所以可以使用socket 传输改数据结构。服务器端主要的实现的功能是用户注册、用于登录账号验证、消息转发、好友添加、用户数据更新，
用户信息查询、存储临时消息到数据库服务器。<br>
服务端用idea 打开需要修改mysql数据库密码在dbcpconfig.properties 配置文件中
## 运行效果 <br>
### 用户登录 <br>
<img src="https://chenyongzhe.github.io/android1.png"  width="400" height="900"><br>
### 用户注册设置头像 <br>
<img src="https://chenyongzhe.github.io/android2.png"  width="400" height="900"><br>
### 好友列表 <br>
<img src="https://chenyongzhe.github.io/android3.png"  width="400" height="900"><br>
### 聊天页面 <br>
<img src="https://chenyongzhe.github.io/android4.png"  width="400" height="900"><br>
### 添加好友 <br>
<img src="https://chenyongzhe.github.io/android5.png"  width="400" height="900"><br>
### 修改个人信息 <br>
<img src="https://chenyongzhe.github.io/android6.png"  width="400" height="900"><br>
