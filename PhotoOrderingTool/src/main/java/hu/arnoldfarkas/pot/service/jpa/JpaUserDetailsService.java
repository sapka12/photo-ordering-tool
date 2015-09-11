package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.domain.model.User;
import hu.arnoldfarkas.pot.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaUserDetailsService")
@Transactional(readOnly = true)
public class JpaUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDetailsService.class);
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("loadUserByUsername({})", username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            LOGGER.warn("user found: {}", user);
            throw new UsernameNotFoundException(username);
        }
        LOGGER.debug("user found: {}", user);
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        }
        LOGGER.debug("{} auth: {}", user.getEmail(), authorities);
        return authorities;
    }
}
