package com.taegyun.tdd.string_calculator;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;

class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    void 빈_문자열_또는_null이면_0을_반환한다() {
        Assertions.assertThat(calculator.add(null)).isEqualTo(0);
        Assertions.assertThat(calculator.add("")).isEqualTo(0);
    }

    @Test
    void 숫자_하나면_해당_숫자를_반환한다() {
        Assertions.assertThat(calculator.add("7")).isEqualTo(7);
    }

    @Test
    void 쉼표_구분자로_숫자의_합을_반환한다() {
        Assertions.assertThat(calculator.add("1,2")).isEqualTo(3);
        Assertions.assertThat(calculator.add("1,2,3")).isEqualTo(6);
        Assertions.assertThat(calculator.add("1, 2, 3")).isEqualTo(6);
    }

    @Test
    void 쉼표_외에_줄바꿈_구분자도_숫자의_합을_반환한다() {
        Assertions.assertThat(calculator.add("1, 2\n 3")).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자를_사용하여_숫자의_합을_반환한다() {
        Assertions.assertThat(calculator.add("//;\n1;2;3")).isEqualTo(6);
        Assertions.assertThat(calculator.add("//|\n1|2|3")).isEqualTo(6);
        Assertions.assertThat(calculator.add("//-\n1-2-3")).isEqualTo(6);
    }

    @Test
    void 음수가_포함되면_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> calculator.add("-1,2,-3"))
                  .isInstanceOf(RuntimeException.class)
                  .hasMessage("음수는 허용되지 않습니다: -1, -3")
                  .hasMessageContaining("-1")
                  .hasMessageContaining("-3");
    }

    @Test
    void 숫자가_아닌_값은_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> calculator.add("ㄱ,2,ㄷ"))
                  .isInstanceOf(RuntimeException.class)
                  .hasMessage("숫자가 아닌 값이 포함되어 있습니다: ㄱ, ㄷ")
                  .hasMessageContaining("ㄱ")
                  .hasMessageContaining("ㄷ");
    }

    @Test
    void _1000_초과_숫자는_무시한다() {
        Assertions.assertThat(calculator.add("2,1001")).isEqualTo(2);
        Assertions.assertThat(calculator.add("1000,1001,999")).isEqualTo(1999);
    }

    @Test
    void 대괄호를_사용하여_여러개의_커스텀_구분자를_사용하여_숫자의_합을_반환한다() {
        Assertions.assertThat(calculator.add("//[*][%]\n1*2%3")).isEqualTo(6);
    }

    @Test
    void 뺄셈_연산을_사용하여_첫번째_숫자에서_나머지_숫자들을_뺀_결과를_반환한다() {
        Assertions.assertThat(calculator.subtract("10,3,2")).isEqualTo(5);
    }

    @Test
    void 뺄셈도_커스텀_구분자를_지원한다() {
        Assertions.assertThat(calculator.subtract("//;\n10;3;2")).isEqualTo(5);
    }
}
