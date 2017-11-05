package it.polimi.deib.deepse.a3e.middleware.loop;

import java.util.ArrayList;
import java.util.List;

import it.polimi.deib.deepse.a3e.middleware.core.A3EFunction;
import it.polimi.deib.deepse.a3e.middleware.domains.Domain;

/**
 * Created by Giovanni on 05/11/17.
 */

public class IdentificationManager {

    public List<Domain> identify(A3EFunction function, List<Domain> domains){

        List<Domain> properDomains = new ArrayList<>();

        for (Domain domain : domains) {
            if (domain.notifyRequirements(function)){
                properDomains.add(domain);
            }
        }

        return properDomains;
    }

}
