package com.paymybuddy.webapp.security;

import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/** Implementation of UserDetailsService. Contains one method to get UserDetails in DB. */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired UserRepository userRepository;

  /**
   * Locates the user based on the email.
   *
   * @param email the email identifying the user whose data is required.
   * @return a fully populated user record (never null)
   * @throws UsernameNotFoundException – if the user could not be found or the user has no
   *     GrantedAuthority
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByEmail(email);

    user.orElseThrow(() -> new UsernameNotFoundException("Not Found UserName: " + email));

    return user.map(UserDetailsImpl::new).get();
  }
}
