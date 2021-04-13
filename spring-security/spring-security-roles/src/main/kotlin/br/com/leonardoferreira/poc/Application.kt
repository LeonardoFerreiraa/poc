package br.com.leonardoferreira.poc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable


@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http {
            cors {
                disable()
            }

            csrf {
                disable()
            }

            authorizeRequests {
                authorize("/**")
            }

            httpBasic {}
        }
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val roleHierarchyImpl = RoleHierarchyImpl()
        roleHierarchyImpl.setHierarchy("ADMIN > BOOK_WRITE AND BOOK_WRITE > BOOK_READ")
        return roleHierarchyImpl
    }

}

@Component
class CustomUserDetailsService : UserDetailsService {
    val users = listOf(
        CustomUserDetails(
            "admin",
            "{noop}password",
            listOf(Permission.ADMIN),
            emptyList()
        ),
        CustomUserDetails(
            "reader",
            "{noop}password",
            listOf(Permission.BOOK_READ),
            listOf(1, 2, 3)
        ),
        CustomUserDetails(
            "writer",
            "{noop}password",
            listOf(Permission.BOOK_WRITE),
            listOf(2, 3, 4)
        )
    ).associateBy { it.username }

    override fun loadUserByUsername(username: String): UserDetails =
        users[username] ?: throw UsernameNotFoundException(username)

}

@RestController
class BookController {

    @GetMapping("/books/{id}")
    @PreAuthorize("hasAuthority('BOOK_READ') AND hasPermission(#id, 'BOOK', 'READ')")
    fun findById(@PathVariable id: Long) = "ok"

    @PutMapping("/books/{id}")
    @PreAuthorize("hasAuthority('BOOK_WRITE') AND hasPermission(#id, 'BOOK', 'WRITE')")
    fun update(@PathVariable id: Long) = "ok"

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun delete(@PathVariable id: Long) = "ok"

}

@Component
class CustomPermissionEvaluator : PermissionEvaluator {

    override fun hasPermission(authentication: Authentication, targetDomainObject: Any, permission: Any): Boolean {
        return true
    }

    override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String, permission: Any): Boolean {
        val customUserDetails = authentication.principal as CustomUserDetails

        if (Permission.ADMIN in customUserDetails.authorities) {
            return true
        }

        return targetId in customUserDetails.idPermissions
    }

}

class CustomUserDetails(
    private val username: String,
    private val password: String,
    private val authorities: List<Permission>,
    val idPermissions: List<Long>
) : UserDetails {

    override fun getUsername(): String =
        username

    override fun getAuthorities(): List<GrantedAuthority> =
        authorities

    override fun getPassword(): String =
        password

    override fun isAccountNonExpired(): Boolean =
        true

    override fun isAccountNonLocked(): Boolean =
        true

    override fun isCredentialsNonExpired(): Boolean =
        true

    override fun isEnabled(): Boolean =
        true

}

enum class Permission : GrantedAuthority {
    ADMIN,
    BOOK_READ,
    BOOK_WRITE;

    override fun getAuthority(): String =
        name

}
