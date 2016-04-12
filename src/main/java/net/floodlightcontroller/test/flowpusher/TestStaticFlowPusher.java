package net.floodlightcontroller.test.flowpusher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import net.floodlightcontroller.core.IOFSwitchListener;
import net.floodlightcontroller.core.PortChangeType;
import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.staticflowentry.IStaticFlowEntryPusherService;
import net.floodlightcontroller.topology.ITopologyService;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFPortDesc;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.action.OFActions;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStaticFlowPusher implements IFloodlightModule, IOFSwitchListener {
	
	protected static Logger logger;
	protected static final int INFINITE_TIMEOUT = 0;
	protected IStaticFlowEntryPusherService flowEntryPusherService;
	protected IOFSwitchService ofSwitchService;
	
	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList
				<Class<? extends IFloodlightService>>();
		l.add(IStaticFlowEntryPusherService.class);
		l.add(ITopologyService.class);
		return l;
	}
	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		logger = LoggerFactory.getLogger(TestStaticFlowPusher.class);
		flowEntryPusherService = context.getServiceImpl(IStaticFlowEntryPusherService.class);
		ofSwitchService = context.getServiceImpl(IOFSwitchService.class);
	}

	@Override
	public void switchAdded(DatapathId switchId) {
		logger.info("Detected a added switch, switch DPID:{}", switchId.toString());
		OFFactory my13Factory = OFFactories.getFactory(OFVersion.OF_13);
		Match myMatch = my13Factory.buildMatch()
				.setExact(MatchField.IN_PORT, OFPort.of(1))
				.setExact(MatchField.IP_PROTO, IpProtocol.ICMP)
				.build();
		ArrayList<OFAction> actionList = new ArrayList<OFAction>();
		OFActions actions = my13Factory.actions();
		OFActionOutput output = actions.buildOutput()
				.setMaxLen(0xFFffFFff)
				.setPort(OFPort.CONTROLLER)
				.build();
		actionList.add(output);
		OFFlowAdd flowAdd = my13Factory.buildFlowAdd()
				.setBufferId(OFBufferId.NO_BUFFER)
				.setHardTimeout(INFINITE_TIMEOUT)
				.setIdleTimeout(INFINITE_TIMEOUT)
				.setPriority(Integer.MAX_VALUE)
				.setMatch(myMatch)
				.setActions(actionList)
				.setTableId(TableId.of(0))
				.build();
		flowEntryPusherService.addFlow(TestStaticFlowPusher.class.getSimpleName(), flowAdd, switchId);
		
	}

	@Override
	public void switchRemoved(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchActivated(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchPortChanged(DatapathId switchId, OFPortDesc port,
			PortChangeType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchChanged(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		ofSwitchService.addOFSwitchListener(this);
	}

}
