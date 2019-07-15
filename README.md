# **Spring boot Authorization Server Application**

**rerequisites:** [Java 8 or 11](https://adoptopenjdk.net/), [gradle](gradle.org),[grafana](https://grafana.com/).

## 入门

运行下面命令安装

```bash
git clone https://github.com/maohhgg/spring-boot-authz-server-example.git
```

> 示例中的 grafana 的地址为[http://118.24.151.27:3000](http://118.24.151.27:3000)，本项目运行的 [http://118.24.151.27:8080/login](http://118.24.151.27:8080/login)，请根据自身实际情况更改地址



### 更新配置文件



本项目没有使用数据库，全部所需数据保存在 `src/main/resources/application.properties` 根据你的实际情况更新配置文件

```ini
#运行端口号
server.port=8080 

#登录用户和密码
oauth.login.user.username=admin
oauth.login.user.password=password

#grafana server的客户端配置
# id secret scopes 三个配置项需要和grafana配置文件中[auth.generic_oauth] client_id client_secret scopes匹配
client.oauth.id=grafana
client.oauth.secret=123456
client.oauth.scopes=user:email
client.oauth.redirectUri=http://118.24.151.27:3000/login/generic_oauth
```

`http://118.24.151.27:3000/login/generic_oauth` 是grafana开启OAuth Authentication后的回调地址。请根据自身实际更新此项。

然后使用 Gradle 启动服务

```shell
gradle bootRun
```

如果你的电脑中没有 Gradle 编译工具，使用下面命令启动，这将配置Gradle编译工具，并编译运行

```shell
./gradlew bootRun
```



### grafana配置文件



如果你的grafana想要局域网访问，不仅仅是本地回环`localhost`，请确保grafana的配置项的`server.root_url `为实际的地址

```ini
...
[server]
...
# The full public facing url you use in browser, used for redirects and emails
# If you use reverse proxy and sub path specify full url (with sub path)
root_url = http://118.24.151.27:3000
...
```

#### generic oauth

示例配置：

```ini
[auth.generic_oauth]
enabled = true
;name = OAuth
allow_sign_up = true
client_id = grafana
client_secret = 123456
scopes = user:email
auth_url = http://118.24.151.27:8080/oauth/authorize
token_url = http://118.24.151.27:8080/oauth/token
api_url = http://118.24.151.27:8080/api/users/me
```

注意 `auth.generic_oauth.name`  配置项不要取消注释，保持`;name = OAuth`这个状态。

重启grfana后，在grafana登录页点击 **Sign in with OAuth** 查看效果。

#### 自动登录

grafana如果需要实现自动登录，需要修改配置为

```ini
...
[auth]
...
oauth_auto_login = true
...
```



### 其他



关于权限，当前没有其他方法，可以在`api_url`中返回相同的用户，`src/main/java/com.demo.spring.AuthorizationServerApplication/UserController`

```java
Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
String name = loggedInUser.getName();

UserProfile profile = new UserProfile();
profile.setName(name);
profile.setEmail(name + "@localhost");

return ResponseEntity.ok(profile);
```

现在是获取登录用户名称，可以返回指定的用户

```java
UserProfile profile = new UserProfile();
profile.setName(‘Admin’);
profile.setEmail("admin@localhost");

return ResponseEntity.ok(profile);
```

关于权限自定义配置请留意 [grafana#9766](https://github.com/grafana/grafana/issues/9766)