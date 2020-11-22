package io.github.cosminseceleanu.tutorials.sampleapp.repository;

import static org.mockito.Mockito.doReturn;

import io.github.cosminseceleanu.tutorials.sampleapp.user.model.User;
import io.github.cosminseceleanu.tutorials.sampleapp.user.repository.UserRepository;
import io.github.cosminseceleanu.tutorials.sampleapp.user.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({MockitoExtension.class})
class UserServiceTest {

  @InjectMocks
  private UserService subject;

  @Mock
  private UserRepository userRepository;


  @Test
  public void testGetById_whenUserNotExists_shouldBeError() {

    doReturn(Mono.empty()).when(userRepository).findById("not");

    Mono<User> actual = subject.get("not");

    StepVerifier.create(actual)
        .expectError()
        .verify();
  }

  @Test
  public void testGetById_whenUserExists_thenReturnUser() {
    User user = User.builder()
        .id("1")
        .email("email")
        .name("name")
        .build();
    doReturn(Mono.just(user)).when(userRepository).findById("not");

    Mono<User> actual = subject.get("not");

    StepVerifier.create(actual)
        .expectNext(user)
        .verifyComplete();
  }

}