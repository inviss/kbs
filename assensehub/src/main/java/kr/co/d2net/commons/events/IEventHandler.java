package kr.co.d2net.commons.events;

public interface IEventHandler<T extends EventArgs> {    
	public void eventReceived(Object sender, T t);
}