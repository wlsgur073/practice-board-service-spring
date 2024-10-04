package com.board.boardpractice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
/*
* webEnvironment의 기본 값은 mocking한 웹 환경을 넣어주는 것이다.
* NONE으로 설정해서 test의 무게를 줄일 수 있다.
* 또한, 설정 클래스를 지정하여 한 번 더 무게를 줄일 수 있다.
* classes의 기본 값은 실행 application의 모든 bean을 scan하기에 별도 클래스를 설정해주는 것이다.
* */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private final PaginationService sut;

    PaginationServiceTest(@Autowired PaginationService sut) {
        this.sut = sut;
    }

    // 해당 test 코드를 설정해줌으로, 다른 팀원들이 해당 test의 barLength의 기본 값이 5인 것을 확인할 수 있다.
    // 스펙의 명세를 바로 코드에 드러내기 위한 목적으로 작성한 코드.
    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrnetBarLength() throws Exception {
        //given

        //when
        int barLength = sut.currentBarLength();

        //then
        assertThat(barLength).isEqualTo(5);
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이징 바 리스트를 만들어준다.")
    @MethodSource // 옵션 설정 안하면 같은 이름의 method 추적
    @ParameterizedTest(name = "[{index}]  page-current: {0}, total: {1} => {2}")
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumber(int currentPageNumber, int totalPages, List<Integer> expected) throws Exception {
        //given

        //when
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber, totalPages);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    // @ParameterizedTest 표현
    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumber() {
        return Stream.of(
                // 검증해 보고 싶은 매개변수 입력 // 현재 페이지 넘버가 가운데 위치하게 설계할 거임.
                arguments(0, 13, List.of(0, 1, 2, 3, 4)), // 현재 게시글이 총 123이니 페이징은 13개
                arguments(1, 13, List.of(0, 1, 2, 3, 4)),
                arguments(2, 13, List.of(0, 1, 2, 3, 4)),
                arguments(3, 13, List.of(1, 2, 3, 4, 5)),
                arguments(4, 13, List.of(2, 3, 4, 5, 6)),
                arguments(5, 13, List.of(3, 4, 5, 6, 7)),
                arguments(6, 13, List.of(4, 5, 6, 7 ,8)),
                arguments(10, 13, List.of(8, 9, 10, 11, 12)),
                arguments(11, 13, List.of(9, 10, 11, 12)),
                arguments(12, 13, List.of(10, 11, 12))
        );
    }
}