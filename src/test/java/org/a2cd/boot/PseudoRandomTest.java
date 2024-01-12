package org.a2cd.boot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class PseudoRandomTest {

    @Test
    void pseudoRandomTest() {
        // 只要种子一样, 随机结果不变, 所有电脑上执行都一样
        var l = List.of(13, 80, 93, 90, 46, 56, 97, 88, 81, 14);

        var l1 = new ArrayList<>();
        var r1 = new Random(10);
        for (int i = 0; i < 10; i++) {
            var n = r1.nextInt(100);
            l1.add(n);
            System.out.print(n +"\t");
        }
        System.out.println();
        var l2 = new ArrayList<>();
        var r2 = new Random(10);
        for (int i = 0; i < 10; i++) {
            var n = r2.nextInt(100);
            l2.add(n);
            System.out.print(n +"\t");
        }

        assertIterableEquals(l, l1);
        assertIterableEquals(l, l2);
        assertIterableEquals(l1, l2);

    }

}
