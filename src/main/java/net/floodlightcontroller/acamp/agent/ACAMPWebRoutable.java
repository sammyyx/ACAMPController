package net.floodlightcontroller.acamp.agent;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

public class ACAMPWebRoutable implements RestletRoutable {

	@Override
	public Restlet getRestlet(Context context) {
		Router router = new Router(context);
		router.attach("/config/json", ACAMPConfigResource.class);
		return router;
	}

	@Override
	public String basePath() {
		return "/wm/ACAMP";
	}

}
