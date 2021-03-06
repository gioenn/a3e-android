package it.polimi.deib.deepse.a3e.middleware.domains;

import it.polimi.deib.deepse.a3e.middleware.core.A3EFunction;
import it.polimi.deib.deepse.a3e.middleware.core.LocationRequirement;
import it.polimi.deib.deepse.a3e.middleware.resolvers.RESTInvocationResolver;
import it.polimi.deib.deepse.a3e.middleware.utils.Commons;

/**
 * Created by giovanniquattrocchi on 03/11/17.
 */

public abstract class RestDomain extends Domain {

    protected RestDomain(String ip, String host, LocationRequirement locationRequirement) {
        super(ip, host, new RESTInvocationResolver(host), locationRequirement);
    }

    @Override
    public boolean ping() {
        latency = Commons.ping(getIp());
        return latency > 0;
    }

    @Override
    public void notifySelection(A3EFunction function) {

    }
}
