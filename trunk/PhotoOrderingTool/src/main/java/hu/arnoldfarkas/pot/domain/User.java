package hu.arnoldfarkas.pot.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "users")
public class User implements UserDetails, Serializable {

    private static final String AUTH_USER = "user";
    private static final Collection<? extends GrantedAuthority> AUTHORITIES = getAuth(AUTH_USER);
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String username;

    public User() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AUTHORITIES;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static Collection<? extends GrantedAuthority> getAuth(String... authorities) {
        Collection<GrantedAuthority> authCollection = new ArrayList<GrantedAuthority>();
        for (String auth : authorities) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(auth);
            authCollection.add(authority);
        }
        return authCollection;
    }
}
