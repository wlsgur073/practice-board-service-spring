package com.board.boardpractice.config;

import com.board.boardpractice.domain.UserAccount;
import com.board.boardpractice.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    /*
    * WebMvcTest는 repository 레이어에 대한 빈을 읽지 않는다.
    * Spring Security를 적용하고 나면 유저 정보를 SecurityConfig class의 UserDetailsService에서 가져온다.
    * 이때 userAccountRepository를 bean으로 설정하지 않았기에 error가 발생할 것이다.
    * 따라서, TestSecurityConfig class를 만들어서 다른 컨트롤러 테스트에 적용시킨다.
    * */

    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod // springTest 실행 전에 해당 method를 먼저 실행시켜 config함
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "userIdForTest",
                "pwd",
                "test@email.com",
                "test",
                "test memo"
                )
        ));
    }
}
