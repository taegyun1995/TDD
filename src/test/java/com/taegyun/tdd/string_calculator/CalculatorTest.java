package com.taegyun.tdd.string_calculator;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;

class CalculatorTest {

    @Test
    void 빈_문자열_또는_null이면_0을_반환한다() {
        Calculator calculator = new Calculator();

        Assertions.assertThat(calculator.add(null)).isEqualTo(0);
        Assertions.assertThat(calculator.add("")).isEqualTo(0);
    }

    @Test
    void 숫자_하나면_해당_숫자를_반환한다() {
        Calculator calculator = new Calculator();

        Assertions.assertThat(calculator.add("7")).isEqualTo(7);
    }
}
