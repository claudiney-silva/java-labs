package effetivo.restapijavaspringboot.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import effetivo.restapijavaspringboot.api.repository.AuthRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return Optional.ofNullable(authRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Auth user not found"));
    }
}