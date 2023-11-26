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

import java.util.Optional;

/**
 * Service to search and find genes
 *
 * @since 0.1.0
 * @author Paul-Christian Volkmer
 */
public interface GeneService {

    /**
     * Returns a list of genes
     * with HGNC ID, EnsemblID, Symbol or name containing the query string
     * case-insensitive.
     *
     * @param queryString A string to query for matching genes
     * @return a list of matching genes
     */
    Iterable<Gene> search(String queryString);

    /**
     * Returns all genes
     *
     * @return a list containing all genes
     */
    Iterable<Gene> findAll();

    /**
     * Returns first gene with matching case-insensitive HGNC ID as <code>Optional</code>
     *
     * @param hgncId A string to find matched gene
     * @return an <code>Optional</code> gene
     */
    Optional<Gene> findByHgncId(String hgncId);

    /**
     * Returns first gene with matching case-insensitive Symbol as <code>Optional</code>
     *
     * @param symbol A string to find matched gene
     * @return an <code>Optional</code> gene
     */
    Optional<Gene> findBySymbol(String symbol);

}
