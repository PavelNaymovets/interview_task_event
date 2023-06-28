package com.interviewTask.event.auth.util;

import com.interviewTask.event.exception.AuthException;
import com.interviewTask.event.exception.HasDeletedStatusException;
import com.interviewTask.event.model.role.Role;
import com.interviewTask.event.model.user.User;
import com.interviewTask.event.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.interviewTask.event.model.user.UserStatus.REMOTE;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private static final Logger authenticationLog = LoggerFactory.getLogger("auth-log");
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AuthException(
                        messageSource.getMessage("authentication.login.password.incorrect", null, Locale.ENGLISH)));

        if (user.getStatus().equals(REMOTE.getStatus())) {
            throw new HasDeletedStatusException(
                    messageSource.getMessage("user.has.deleted.status", null, Locale.ENGLISH));
        }

        authenticationLog.info(messageSource.getMessage(
                "authentication.user.successfully.load", null, Locale.ENGLISH) + login);

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
