package com.github.chaosfirebolt.rncb.app;

import com.github.chaosfirebolt.generator.identifier.api.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

  private static final int DEFAULT_PER_MINUTE_LIMIT = 10;
  private static final int DEFAULT_PER_HOUR_LIMIT = 100;

  private final IdentifierGenerator<String> appIdGenerator;
  private final IdentifierGenerator<String> appPasswordGenerator;
  private final PasswordEncoder passwordEncoder;
  private final ApplicationRepository repository;

  @Autowired
  public ApplicationService(IdentifierGenerator<String> appIdGenerator, IdentifierGenerator<String> appPasswordGenerator,
                            PasswordEncoder passwordEncoder, ApplicationRepository repository) {
    this.appIdGenerator = appIdGenerator;
    this.appPasswordGenerator = appPasswordGenerator;
    this.passwordEncoder = passwordEncoder;
    this.repository = repository;
  }

  public ApplicationResponse register() {
    Application newApp = new Application();
    newApp.setApplicationId(appIdGenerator.generate(id -> repository.findByApplicationId(id).isEmpty()));
    String rawPassword = appPasswordGenerator.generate();
    newApp.setApplicationPassword(passwordEncoder.encode(rawPassword));
    newApp.setLimitPerMinute(DEFAULT_PER_MINUTE_LIMIT);
    newApp.setLimitPerHour(DEFAULT_PER_HOUR_LIMIT);

    newApp = repository.save(newApp);
    return new ApplicationResponse(newApp.getApplicationId(), rawPassword);
  }
}
