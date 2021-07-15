package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // セキュリティ用クラス
@Configuration // Bean
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // webSecurityConfigurerAdapterのメソッドをオーバーライドして設定する

    @Bean // @Configurationに@Beanを登録
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** セキュリティの対象外を設定 */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/webjars/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**");
    }

    /** セキュリティの各種設定 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ログイン不要ページ
        http.authorizeRequests()
                .antMatchers("/login").permitAll() // 直リンクOK
                .antMatchers("/user/signup").permitAll() // 直リンクOK
                .anyRequest().authenticated(); // それ以外直リンク不可

        // ログイン処理
        http.formLogin()
                .loginProcessingUrl("/login") // formの th:action="@{/login}" と一致させる
                .loginPage("/login") // システムのログインページリンク先
                .failureUrl("/login?error") // 失敗時遷移先
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/user/list", true); // 成功後遷移先

        http.csrf().disable(); // CSRF設定を無効化
    }

    /** 認証の設定 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // SpringSecurityはパスワード暗号化しないと動作しない
        PasswordEncoder encoder = passwordEncoder();

        // インメモリ認証
        auth.inMemoryAuthentication()
                .withUser("user")
                    .password(encoder.encode("user"))
                    .roles("GENERAL")
                .and()
                .withUser("admin")
                    .password(encoder.encode("admin"))
                    .roles("ADMIN");

    }
}
