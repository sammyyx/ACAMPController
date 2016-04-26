package net.floodlightcontroller.acamp.agent;

public class ACAMPFsm {
	
	//初始状态
	public ACAMPState mState = ACAMPState.DOWN;
	public void acceptRegistry() {
		mState.acceptRegistry(this);
	}
	public void denyRegistry() {
		mState.denyRegistry(this);
	}
	public void recvConfigReq() {
		mState.recvConfigReq(this);
	}
	public void releaseControl() {
		mState.releaseControl(this);
	}
	public void timeout() {
		mState.timeout(this);
	}
}
