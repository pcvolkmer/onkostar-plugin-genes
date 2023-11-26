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

/**
 * Exception to be thrown if an error occurs searching genes
 *
 * @since 0.1.0
 * @author Paul-Christian Volkmer
 */
public class GenesPluginException extends RuntimeException {

    public GenesPluginException(final String msg) {
        super(msg);
    }

}
