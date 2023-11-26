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

import de.itc.onkostar.api.Disease;
import de.itc.onkostar.api.Procedure;
import de.itc.onkostar.api.analysis.AnalyzerRequirement;
import de.itc.onkostar.api.analysis.IProcedureAnalyzer;
import de.itc.onkostar.api.analysis.OnkostarPluginType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Plugin implementation
 * Provides methods exposed to Onkostar
 *
 * @since 0.1.0
 * @author Paul-Christian Volkmer
 */
@Component
public class GenesPlugin implements IProcedureAnalyzer {

    private final GeneService geneService;

    public GenesPlugin(GeneService geneService) {
        this.geneService = geneService;
    }

    @Override
    public OnkostarPluginType getType() {
        return OnkostarPluginType.BACKEND_SERVICE;
    }

    @Override
    public String getVersion() {
        return "0.1.0";
    }

    @Override
    public String getName() {
        return "Gene Plugin";
    }

    @Override
    public String getDescription() {
        return "Plugin zum Bereitstellen von Gennamen und Gen-Codes";
    }

    @Override
    public boolean isSynchronous() {
        return false;
    }

    @Override
    public AnalyzerRequirement getRequirement() {
        return AnalyzerRequirement.PROCEDURE;
    }

    @Override
    public boolean isRelevantForDeletedProcedure() {
        return false;
    }

    @Override
    public boolean isRelevantForAnalyzer(final Procedure procedure, final Disease disease) {
        return false;
    }

    @Override
    public void analyze(final Procedure procedure, final Disease disease) {
        // Nothing to do - should never be called
    }

    /**
     * Return list with ATC codes and agents.
     * Usage in script:
     *
     * <pre>
     *      executePluginMethod(
     *          'GenesPlugin',
     *          'search',
     *          { q: 'BRAF' },
     *          function (result) {console.log(result);},
     *          false
     *      );
     * </pre>
     *
     * @param input The data Map
     * @return The result list filtered by input
     */
    public Iterable<Gene> search(final Map<String, Object> input) {
        String queryString = "";
        if (null != input.get("q")) {
            queryString = input.get("q").toString();
        } else {
            throw new RuntimeException("No genes for empty query string");
        }

        return geneService.search(queryString);
    }

    /**
     * Return list with ATC codes and agents.
     * Usage in script:
     *
     * <pre>
     *      executePluginMethod(
     *          'GenesPlugin',
     *          'findBySymbol',
     *          { symbol: 'BRAF' },
     *          function (result) {console.log(result);},
     *          false
     *      );
     * </pre>
     *
     * @param input The data Map
     * @return The result list filtered by input
     */
    public Gene findBySymbol(final Map<String, Object> input) {
        String queryString = "";
        if (null != input.get("symbol")) {
            queryString = input.get("symbol").toString();
        } else {
            throw new RuntimeException("No genes for empty symbol");
        }

        var result = geneService.findBySymbol(queryString);

        if (result.isPresent()) {
            return result.get();
        }

        throw new RuntimeException(String.format("No gene found for symbol '%s'", queryString));
    }
}
