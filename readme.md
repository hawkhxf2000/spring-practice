# Springboot-Shiro整合基础学习

1. 导入shiro依赖

~~~
<!--        为了跟视频教程保持一致，暂时使用与教程一致的1.5.3版本，等项目跑通以后再更换为1.9.0最新版看看是否也能运行-->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.5.3</version>
</dependency>
~~~

2. 在realm包中创建Realm类,继承AuthorizingRealm并实现其两个方法
~~~
public class AccountRealm extends AuthorizingRealm {

    //注入service层，以便于后续从数据库中根据用户输入的用户名查找记录
    @Autowired
    private AccountService accountService;

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //从数据库中查询出用户名与token中的用户名相同的记录。
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",token.getUsername());
        Account account = accountService.getOne(queryWrapper);

        //当数据库中有对应用户名的记录时，将记录中的密码与token中的密码进行比较，返回一个SimpleAuthenticationInfo，如果不存在记录，则返回null，shiro会抛出一个异常
        if(null != account){
            return new SimpleAuthenticationInfo(account,account.getPassword(),getName());
        }
        return null;
    }
}
~~~

3. 在config包中创建Shiro配置类ShiroConfig
~~~
@Configuration
public class ShiroConfig {

    /**
     * step 3: 将DefaultWebSecurityManager对象注入ShiroFilterFactoryBean对象，并注入IOC容器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        return factoryBean;
    }

    /**
     * step 2: 将AccountRealm对象注入DefaultWebSecurityManager对象，并将这个对象注入IOC容器
     * @param accountRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accountRealm") AccountRealm accountRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        return securityManager;
    }

    /**
     * step 1: 创建一个AccountRealm对象，注入IOC容器
     * @return
     */
    @Bean
    public AccountRealm accountRealm(){
        return new AccountRealm();
    }
}
~~~

4. 在main/resources/templates文件夹下创建四个页面： index.html, main.html, manager.html, administrator.html，以演示不同权限与角色设置的效果

5. 在ShiroConfig类的shiroFilterFactoryBean中加入过滤器以分配权限
~~~
/**
     * step 3: 将DefaultWebSecurityManager对象注入ShiroFilterFactoryBean对象，并注入IOC容器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        //权限设置
        Map<String,String> map = new HashMap<>();
        map.put("/index","anon");   //index主页任何人都可以访问
        map.put("/main","authc");   //main页面必须登录以后才能访问
        map.put("/manager","perms[manage]");   //manager页面需要有manager权限的人才能访问
        map.put("/administrator","roles[administrator]");  //administrator页面需要有administrator角色的人才能访问
        factoryBean.setFilterChainDefinitionMap(map);
        
        return factoryBean;
    }
~~~
此时如果输入localhost:8080/index可以访问index页面，但是跳转到其他三个页面时，因为没有登录数据，所以都无法显示，而且会被shiro转到默认的login.jsp去。

6. 增加index.html页面
~~~
<body>
<form action="/login" method="post">
    <table>
        <tr>
            <td>用户名：</td>
            <td>
                <input type="text" name="username" placeholder="请输入用户名">
            </td>
        </tr>
        <tr>
            <td>密码：</td>
            <td>
                <input type="text" name="password" placeholder="请输入密码">
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="登录">
            </td>
        </tr>
    </table>
</form>
</body>
~~~

7. 在MyController类中新建方法login(), 接收登录页传过来的登录信息并将其存入Token中，并利用shiro的Subject类将其封装，以便shiro框架进行身份校验
~~~
MyController.java

@PostMapping("/login")
    public String login(String username, String password, Model model) {
        Subject subject = SecurityUtils.getSubject(); //创建一个shiro框架的Subject对象，注意导入类时一定要选择shiro包
        UsernamePasswordToken token = new UsernamePasswordToken(username, password); //将用户输入的username与password放入token中
        /**
         * 将token交给shiro，进入我们自定义的AccountRealm中的doGetAuthenticationInfo方法中去进行登录认证（这里的token就是方法中的authenticationToken）
         * 当登录成功后跳转到index页面，登录不成功跳转回login页面。
         * 使用try-catch捕获异常，并利用Model类接收异常信息，传给前端网页。
         */
        try {
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg","用户名错误！");
            return "login";
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            model.addAttribute("errorMsg","密码错误！");
            return "login";
        }
    }
~~~

8. 使用Thymeleaf将Model类获得的异常信息传递给login页面
~~~
login.html
<head>
    <meta charset="UTF-8" xmlns:th="http://www.thymeleaf.org">
    <title>Title</title>
    <link rel="shortcut icon" href="#">
</head>
<body>
<form action="/login" method="post">
    <table>
        <span th:text="${errorMsg}"></span>
        <tr>
            <td>用户名：</td>
            <td>
                <input type="text" name="username" placeholder="请输入用户名">
            </td>
        </tr>
        <tr>
            <td>密码：</td>
            <td>
                <input type="password" name="password" placeholder="请输入密码">
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="登录">
            </td>
        </tr>
    </table>
</form>
</body>
~~~

9. 对登录用户进行权限授权
~~~
AccountRealm.java

/**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录用户信息
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account)subject.getPrincipal(); //从subject中获取的是一个object对象，需要对其进行cast

        //设置角色
        Set<String> roles = new HashSet<>();
        roles.add(account.getRole());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //设置权限
        info.addStringPermission(account.getPerms());

        return info;
    }
~~~

10. 校验未成功返回未授权信息
~~~
ShiroConfig.java

 @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ......
        
        //设置提示未授权页面链接
        factoryBean.setUnauthorizedUrl("/unauth");
        
        return factoryBean;
    }
~~~
~~~
MyController.java

     /**
     * 返回未授权信息（空白页面加字符串）
     * @return
     */
    @GetMapping("/unauth")
    @ResponseBody
    public String unAuth(){
        return "页面未授权，无法访问";
    }
~~~

11. 将用户名信息利用session传递到页面，使用thymeleaf渲染
~~~
MyController.java

@PostMapping("/login")
    public String login(String username, String password, Model model) {
        。。。。。。
        
        try {
            subject.login(token);
            //将用户信息存入session 的 account对象中，便于后续在网页上取出用户信息
            Account account = (Account)subject.getPrincipal();
            subject.getSession().setAttribute("username",account.getUsername());
            return "index";
        
        。。。。。。
    }

~~~

~~~
index.html

......

<h1>Index</h1>
<div th:if="${session.username} != null">
    <span th:text = "${session.username} +', 欢迎回来' " style="font-size: 14px"></span>
    <a href="logout" style="margin-left: 20px; font-size:14px">退出</a>
</div>

......
~~~

12. 使用subject中的logout方法实现logout功能
~~~
MyController.java

/**
     * logout功能
     * @return
     */
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
~~~

~~~
index.html

......

<h1>Index</h1>
<div th:if="${session.username} != null">
    <span th:text = "${session.username} +', 欢迎回来' " style="font-size: 14px"></span>
    <a href="logout" style="margin-left: 20px; font-size:14px">退出</a>
</div>

......
~~~
