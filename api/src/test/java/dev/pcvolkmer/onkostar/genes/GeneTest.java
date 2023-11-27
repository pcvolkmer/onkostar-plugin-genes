/*
 * This file is part of onkostar-plugin-genes
 *
 * Copyright (C) 2023  Paul-Christian Volkmer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.pcvolkmer.onkostar.genes;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneTest {

    @ParameterizedTest(name = "Expect \"{0}\" to result in {1}")
    @MethodSource("chromosomeInPropertyFormSource")
    void testShouldReturnChromosomesInPropertyForm(String chromsome, List<String> expectedResult) {
        var gene = new Gene("", "", "", "", chromsome);

        assertThat(gene.getChromosomesInPropertyForm()).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> chromosomeInPropertyFormSource() {
        return Stream.of(
                Arguments.of("19q13.43", List.of("chr19")),
                Arguments.of("22q13 alternate reference locus", List.of("chr22")),
                Arguments.of("Xq28 and Yq12", List.of("chrX", "chrY")),
                Arguments.of("mitochondria", List.of()),
                Arguments.of("reserverd", List.of()),
                Arguments.of("unplaced", List.of()),
                Arguments.of("", List.of())
        );
    }

    @ParameterizedTest(name = "Expect \"{0}\" to return single chromosome: {1}")
    @MethodSource("hasSingleChromosomeInPropertyFormSource")
    void testShouldReturnSingleChromosomeInPropertyForm(String chromsome, boolean expectedResult) {
        var gene = new Gene("", "", "", "", chromsome);

        if (expectedResult) {
            assertThat(gene.getSingleChromosomeInPropertyForm()).isPresent();
        } else {
            assertThat(gene.getSingleChromosomeInPropertyForm()).isEmpty();
        }
    }

    private static Stream<Arguments> hasSingleChromosomeInPropertyFormSource() {
        return Stream.of(
                Arguments.of("19q13.43", true),
                Arguments.of("22q13 alternate reference locus", true),
                Arguments.of("Xq28 and Yq12", false),
                Arguments.of("mitochondria", false),
                Arguments.of("reserverd", false),
                Arguments.of("unplaced", false),
                Arguments.of("", false)
        );
    }

}
