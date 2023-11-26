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

import java.util.Objects;

/**
 * Represents a gene
 *
 * @since 0.1.0
 * @author Paul-Christian Volkmer
 */
public class Gene {

    private final String hgncId;

    private final String ensembleId;

    private final String symbol;

    private final String name;

    private final String chromosome;

    Gene(
            String hgncId,
            String ensembleId,
            String symbol,
            String name,
            String chromosome
    ) {
        this.hgncId = hgncId;
        this.ensembleId = ensembleId;
        this.symbol = symbol;
        this.name = name;
        this.chromosome = chromosome;
    }

    /**
     * Returns the HGNC ID of this gene
     * @return the HGNC ID
     */
    public String getHgncId() {
        return hgncId;
    }

    /**
     * Returns the EnsemblID of this gene
     * @return the EnsemblID
     */
    public String getEnsemblId() {
        return ensembleId;
    }

    /**
     * Returns the Symbol of this gene
     * @return the Symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the name of this gene
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the chromosome the gene is located at
     * @return the chromosome
     */
    public String getChromosome() {
        return chromosome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gene gene = (Gene) o;
        return Objects.equals(hgncId, gene.hgncId) && Objects.equals(ensembleId, gene.ensembleId) && Objects.equals(symbol, gene.symbol) && Objects.equals(name, gene.name) && Objects.equals(chromosome, gene.chromosome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hgncId, ensembleId, symbol, name, chromosome);
    }
}
