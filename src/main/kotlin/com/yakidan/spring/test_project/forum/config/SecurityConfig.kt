package com.yakidan.spring.test_project.forum.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Autowired val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter(
) {

    //refactor
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/register").permitAll()
            .antMatchers("/api/v1/").authenticated()
            .anyRequest().authenticated()
            .and().httpBasic()
    }

    @Bean
    protected fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val dao = DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder())
        dao.setUserDetailsService(userDetailsService)
        return dao
    }

    @Override
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }


    @Bean
    protected fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }
}