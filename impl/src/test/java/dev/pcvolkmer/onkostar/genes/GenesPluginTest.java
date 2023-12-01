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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenesPluginTest {

    @Mock
    private GeneService geneService;

    private GenesPlugin plugin;

    @BeforeEach
    void setup() {
        this.plugin = new GenesPlugin(geneService);
    }

    @Test
    void testShouldSearchForGene() {
        plugin.search(Map.of("q", "BRAF"));

        var captor = ArgumentCaptor.forClass(String.class);
        verify(geneService, times(1)).search(captor.capture());
        assertThat(captor.getValue()).isEqualTo("BRAF");
    }

    @Test
    void testShouldRequestGeneBySymbol() {
        doAnswer(invocationOnMock -> Optional.of(
                new Gene(
                        "HGNC:1097",
                        "ENSG00000157764",
                        "BRAF",
                        "B-Raf proto-oncogene, serine/threonine kinase",
                        "7q34"
                )
        )).when(geneService).findBySymbol(anyString());

        plugin.findBySymbol(Map.of("symbol", "BRAF"));

        var captor = ArgumentCaptor.forClass(String.class);
        verify(geneService, times(1)).findBySymbol(captor.capture());
        assertThat(captor.getValue()).isEqualTo("BRAF");
    }

    @Test
    void testShouldThrowExceptionIfGeneNotFound() {
        doAnswer(invocationOnMock -> Optional.empty()).when(geneService).findBySymbol(anyString());

        var message = assertThrows(GenesPluginException.class, () -> plugin.findBySymbol(Map.of("symbol", "WHAT?"))).getMessage();

        assertThat(message).isEqualTo("No gene found for symbol 'WHAT?'");

        var captor = ArgumentCaptor.forClass(String.class);
        verify(geneService, times(1)).findBySymbol(captor.capture());
        assertThat(captor.getValue()).isEqualTo("WHAT?");
    }

    @Test
    void testShouldThrowExceptionIfEmptySymbolString() {
        var message = assertThrows(GenesPluginException.class, () -> plugin.findBySymbol(Map.of("symbol", ""))).getMessage();

        assertThat(message).isEqualTo("No gene found for symbol ''");

        verify(geneService, times(1)).findBySymbol(anyString());
    }

    @Test
    void testShouldThrowExceptionIfNoSymbol() {
        var message = assertThrows(GenesPluginException.class, () -> plugin.findBySymbol(Map.of())).getMessage();

        assertThat(message).isEqualTo("No genes for empty symbol");

        verify(geneService, never()).findBySymbol(anyString());
    }

    @Test
    void testShouldThrowExceptionIfNoQuery() {
        var message = assertThrows(GenesPluginException.class, () -> plugin.search(Map.of())).getMessage();

        assertThat(message).isEqualTo("No genes for empty query string");

        verify(geneService, never()).findBySymbol(anyString());
    }

}
