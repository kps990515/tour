package com.flab.tour.common.util;

import java.util.UUID;

public class OrderedUUIDGenerator {
    public static UUID generateOrderedUUID() {
        // 기본 UUID 생성
        long currentTime = System.currentTimeMillis();
        long msb = currentTime << 32; // 상위 64비트에 중 앞 32비트에 시간 정보 배치
        long lsb = currentTime & 0xFFFFFFFFL; // 하위 64비트 중 뒤 32비트에 시간 정보 배치

        // msb와 lsb의 나머지 부분은 랜덤 값으로 채움
        msb |= (long) (Math.random() * 0xFFFFFFFFL); // 상위 64비트 중 뒤 32비트에 랜덤값
        lsb |= ((long) (Math.random() * 0xFFFFFFFFL)) << 32; // 하위 64비트 중 앞 32비트에 랜덤값

        return new UUID(msb, lsb);
    }
}
