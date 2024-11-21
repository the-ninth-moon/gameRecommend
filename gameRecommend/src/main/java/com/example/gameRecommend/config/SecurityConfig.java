package com.example.gameRecommend.config;

import com.example.gameRecommend.utils.Result;
import com.example.gameRecommend.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Resource
//    CustomUserServiceImpl customUserService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
     * 解决Security访问Swagger2被拦截的问题；
     * */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        System.out.println(1);
//        // allow Swagger URL to be accessed without authentication
//        web.ignoring().antMatchers(
//                "/swagger-ui.html",
//                "/v2/api-docs", // swagger api json
//                "/swagger-resources/configuration/ui", // 用来获取支持的动作
//                "/swagger-resources", // 用来获取api-docs的URI
//                "/swagger-resources/configuration/security", // 安全选项
//                "/swagger-resources/**",
//                "/webjars/**"
//
//
//        );
//    }
    @Override
    public void configure(WebSecurity web) {
        WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();
        ignoring.antMatchers(String.valueOf(HttpMethod.POST), "/user/register");//nice，硬解码就硬解码，无所谓了
    }


    //要有一个configure方法吧Service整进来 为什么会不需要
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        System.out.println(2);
//        auth.userDetailsService(customUserService);
//        //基于内存来存储用户信息
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin").password("123").roles("USER","ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //System.out.println(3);
        // System.out.println(customUserService.loadUserByUsername("admin"));
        http.csrf().disable();
        http.authorizeRequests()
                //允许根路径url的访问
                .antMatchers("/","/blog/getByPage","/game/getByPage","/game-image/gameImage/1").permitAll()
                //允许swagger-ui.html访问
                .antMatchers("/swagger-ui.html").permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/default") // add page where to navigate after login
                .usernameParameter("userName") // make sure your form has correct params
                .passwordParameter("password") // make sure your form has correct params
                .permitAll() // allow access
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        //如果登录成功就返回一段json
                        resp.setContentType("application/json;charset=utf-8");
                        //这是往出写的
                        PrintWriter out = resp.getWriter();
                        //登录成功的hr对象
                        User user = (User)authentication.getPrincipal();
                        System.out.println(user.toString());
                        //为了安全，这里返回给前端时将密码设置为null
                        user.setPassword(null);
                        Result ok = Result.success(200,"登录成功！",user);
                        //把Hr写成字符串
                        String s = new ObjectMapper().writeValueAsString(ok);
                        //把字符串写出去
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        //如果登录失败也返回一段json
                        resp.setContentType("application/json;charset=utf-8");
                        //设置状态码
                        //resp.setStatus(401);

                        //这是往出写的
                        PrintWriter out = resp.getWriter();
                        Result result = Result.fail("登录失败！");
                        if(exception instanceof LockedException){
                            result.setMsg("账户被锁定，请联系管理员！");
                        }else if (exception instanceof CredentialsExpiredException){
                            result.setMsg("密码过期，请联系管理员！");
                        }else if (exception instanceof DisabledException){
                            result.setMsg("账户被禁用，请联系管理员！");
                        }else if (exception instanceof BadCredentialsException){
                            result.setMsg("用户名或者密码输入错误，请重新输入！");
                        }
                        out.write(new ObjectMapper().writeValueAsString(result));
                        out.flush();
                        out.close();

                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp,
                                                Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(Result.success("注销成功！")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable();
    }
}