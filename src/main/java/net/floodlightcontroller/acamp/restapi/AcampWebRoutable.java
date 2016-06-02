package net.floodlightcontroller.acamp.restapi;

import net.floodlightcontroller.restserver.RestletRoutable;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class AcampWebRoutable implements RestletRoutable {

	@Override
	public Restlet getRestlet(Context context) {
		Router router = new Router(context);
		router.attach("/{apid}/config/json", AcampConfigResource.class);
		router.attach("/{apid}/statistic/json", AcampStatisticResource.class);
		router.attach("/{apid}/{operation}/{macAddress}/json", AcampStationResource.class);
		router.attach("/{apid}/{operation}/{macAddress}/json", AcampStationResource.class);
		return router;
	}

	@Override
	public String basePath() {
		return "/wm/acamp";
	}

}
