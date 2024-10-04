package com.board.boardpractice.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        // start와 end 계산
        int start =  Math.max(currentPageNumber - (BAR_LENGTH / 2), 0); // 중앙 값으로 위치하기 위해
        int end = Math.min(start + BAR_LENGTH, totalPages);

        return IntStream.range(start, end).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
