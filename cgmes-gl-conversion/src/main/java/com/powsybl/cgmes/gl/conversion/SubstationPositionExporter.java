/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.conversion;

import com.powsybl.iidm.network.Substation;
import com.powsybl.triplestore.api.TripleStore;
import org.gridsuite.geodata.extensions.SubstationPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * @author Massimo Ferraro <massimo.ferraro@techrain.eu>
 */
public class SubstationPositionExporter extends AbstractPositionExporter {

    private static final Logger LOG = LoggerFactory.getLogger(SubstationPositionExporter.class);

    public SubstationPositionExporter(TripleStore tripleStore, ExportContext context) {
        super(tripleStore, context);
    }

    public void exportPosition(Substation substation) {
        Objects.requireNonNull(substation);
        SubstationPosition substationPosition = substation.getExtension(SubstationPosition.class);
        if (substationPosition == null) {
            LOG.warn("Cannot find position data of substation {}, name {}: skipping export of substation position", substation.getId(), substation.getName());
            return;
        }
        String locationId = addLocation(substation.getId(), substation.getName());
        addLocationPoint(locationId, substationPosition.getCoordinate(), 0);
    }

}
