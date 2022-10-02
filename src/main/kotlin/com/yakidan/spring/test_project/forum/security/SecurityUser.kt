package com.yakidan.spring.test_project.forum.security

import lombok.Data
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

@Data
class SecurityUser(
    private val username: String,
    private val password: String,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("Role"));
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true;
    }

    override fun isAccountNonLocked(): Boolean {
        return true;
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun fromUser(
            user:
            com.yakidan.spring.test_project.forum.entity.User
        ):
                UserDetails {
            val user: User = User(
                user.email,
                user.password,
                true,
                true,
                true,
                true,
                mutableListOf(SimpleGrantedAuthority("Role"))
            )

            println("SecurityUser" + user)
            return user
        }
    }
}