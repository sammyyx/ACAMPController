package net.floodlightcontroller.acamp.agent;

public enum ACAMPState {
	DOWN{

		@Override
		public void acceptRegistry(ACAMPFsm acamp) {
			acamp.mState = REGISTRY;
		}

		@Override
		public void denyRegistry(ACAMPFsm acamp) {
			
		}

		@Override
		public void recvConfigReq(ACAMPFsm acamp) {
			System.out.println("invalid state");
		}

		@Override
		public void releaseControl(ACAMPFsm acamp) {
			System.out.println("invalid state");
		}

		@Override
		public void timeout(ACAMPFsm acamp) {
			
		}},
	REGISTRY{

		@Override
		public void acceptRegistry(ACAMPFsm acamp) {
			System.out.println("invalid state");
		}

		@Override
		public void denyRegistry(ACAMPFsm acamp) {
			System.out.println("invalid state");
		}

		@Override
		public void recvConfigReq(ACAMPFsm acamp) {
			acamp.mState = RUN;
		}

		@Override
		public void releaseControl(ACAMPFsm acamp) {
			System.out.println("invalid state");			
		}

		@Override
		public void timeout(ACAMPFsm acamp) {
			
		}},
	RUN{

		@Override
		public void acceptRegistry(ACAMPFsm acamp) {
			System.out.println("invalid state");
		}

		@Override
		public void denyRegistry(ACAMPFsm acamp) {
			System.out.println("invalid state");
		}

		@Override
		public void recvConfigReq(ACAMPFsm acamp) {
			System.out.println("invalid state");			
		}

		@Override
		public void releaseControl(ACAMPFsm acamp) {
			acamp.mState = DOWN;
		}

		@Override
		public void timeout(ACAMPFsm acamp) {
			acamp.mState = DOWN;
		}};
	
	//事件
	public abstract void acceptRegistry(ACAMPFsm acamp);
	public abstract void denyRegistry(ACAMPFsm acamp);
	public abstract void recvConfigReq(ACAMPFsm acamp);
	public abstract void releaseControl(ACAMPFsm acamp);
	public abstract void timeout(ACAMPFsm acamp);
	
}
