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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class GenesCsvTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Test
    void testCsvFileForUniqueHgncIds() throws Exception {
        doAnswer(invocationOnMock -> new ClassPathResource("genes.csv")).when(resourceLoader).getResource(anyString());
        var genes = new GeneServiceImpl(resourceLoader).loadGenes(resourceLoader);

        assertThat(
                genes.stream().map(Gene::getHgncId).distinct().count()
        ).isEqualTo(genes.size()).describedAs("unique HGNC IDs");
    }

    @Test
    void testCsvFileForUniqueSymbols() throws Exception {
        doAnswer(invocationOnMock -> new ClassPathResource("genes.csv")).when(resourceLoader).getResource(anyString());
        var genes = new GeneServiceImpl(resourceLoader).loadGenes(resourceLoader);

        assertThat(
                genes.stream().map(Gene::getSymbol).distinct().count()
        ).isEqualTo(genes.size()).describedAs("unique Symbols IDs");
    }

}
