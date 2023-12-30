package com.github.chaosfirebolt.rncb.config;

import com.github.chaosfirebolt.rncb.app.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ApplicationDetailsService implements UserDetailsService {

  private final ApplicationRepository repository;

  @Autowired
  public ApplicationDetailsService(ApplicationRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String applicationId) throws UsernameNotFoundException {
    return repository.findByApplicationId(applicationId)
            .map(app -> new User(app.getApplicationId(), app.getApplicationPassword(), Set.of()))
            .orElseThrow(() -> new UsernameNotFoundException("Application not found"));
  }
}
