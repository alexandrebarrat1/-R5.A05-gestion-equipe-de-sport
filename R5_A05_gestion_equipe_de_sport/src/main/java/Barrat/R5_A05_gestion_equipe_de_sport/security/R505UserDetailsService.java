package Barrat.R5_A05_gestion_equipe_de_sport.security;


import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class R505UserDetailsService implements UserDetailsService {


    private Map<String, UserDetails> users = new HashMap<>();
    private PasswordEncoder encoder;

    public R505UserDetailsService(PasswordEncoder encoder) {
    	this.encoder = encoder;
    }
    
    public void createUser() {
    	this.users = new HashMap<>();
    	users.put("admin", User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build());
        users.put("user", User.withUsername("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	this.createUser();
        UserDetails u = users.get(username);
        if (u == null) throw new UsernameNotFoundException(username);
        return u;
    }
}

