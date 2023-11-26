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

import org.apache.commons.csv.CSVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation to search and find genes
 *
 * @since 0.1.0
 * @author Paul-Christian Volkmer
 */
@Service
public class GeneServiceImpl implements GeneService {

    private final Logger logger = LoggerFactory.getLogger(GeneService.class);

    private final List<Gene> genes;

    public GeneServiceImpl(final ResourceLoader resourceLoader) {
        this.genes = loadGenes(resourceLoader);
    }

    List<Gene> loadGenes(final ResourceLoader resourceLoader) {
        var result = new ArrayList<Gene>();

        try {
            var inputStream = resourceLoader.getResource("genes.csv").getInputStream();
            var parser = CSVFormat.RFC4180
                    .withHeader()
                    .withSkipHeaderRecord()
                    .withDelimiter('\t')
                    .parse(new InputStreamReader(inputStream));
            for (var row : parser) {
                result.add(
                        new Gene(
                                row.get("HGNC ID"),
                                row.get("Ensembl ID(supplied by Ensembl)"),
                                row.get("Approved symbol"),
                                row.get("Approved name"),
                                row.get("Chromosome")
                        )
                );
            }

            logger.info("{} genes available", result.size());

            return result;
        } catch (IOException e) {
            logger.error("Cannot load genes");
        }

        return result;
    }


    @Override
    public Iterable<Gene> search(String queryString) {
        return this.genes.stream().filter(gene -> gene.getHgncId().toLowerCase().contains(queryString.toLowerCase())
                || gene.getEnsemblId().toLowerCase().contains(queryString.toLowerCase())
                || gene.getSymbol().toLowerCase().contains(queryString.toLowerCase())
                || gene.getName().toLowerCase().contains(queryString.toLowerCase())
        ).collect(Collectors.toList());
    }

    @Override
    public Iterable<Gene> findAll() {
        return this.genes;
    }

    @Override
    public Optional<Gene> findByHgncId(String hgncId) {
        return this.genes.stream().filter(gene -> gene.getHgncId().equalsIgnoreCase(hgncId)).findFirst();
    }

    @Override
    public Optional<Gene> findBySymbol(String symbol) {
        return this.genes.stream().filter(gene -> gene.getSymbol().equalsIgnoreCase(symbol)).findFirst();
    }
}
