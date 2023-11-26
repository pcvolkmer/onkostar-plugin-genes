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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class GeneServiceImplTest {

    @Mock
    private ResourceLoader resourceLoader;

    private GeneServiceImpl service;

    @BeforeEach
    void setup() {
        doAnswer(invocationOnMock -> new ClassPathResource("genes.csv")).when(resourceLoader).getResource(anyString());
        this.service = new GeneServiceImpl(resourceLoader);
    }

    @Test
    void testShouldReturnDistinctListWithGenesMatchingQueryString() {
        var actual = service.search("BRAF");
        assertThat(actual).hasSize(3);
    }

    @Test
    void testShouldReturnGeneByHgncId() {
        var actual = service.findByHgncId("HGNC:1097");
        assertThat(actual).hasValueSatisfying(GeneServiceImplTest::assertBRAFGene);
    }

    @Test
    void testShouldReturnGeneBySymbol() {
        var actual = service.findBySymbol("BRAF");
        assertThat(actual).hasValueSatisfying(GeneServiceImplTest::assertBRAFGene);
    }

    private static void assertBRAFGene(Gene gene) {
        assertThat(gene.getHgncId()).isEqualTo("HGNC:1097");
        assertThat(gene.getEnsemblId()).isEqualTo("ENSG00000157764");
        assertThat(gene.getSymbol()).isEqualTo("BRAF");
    }

}
