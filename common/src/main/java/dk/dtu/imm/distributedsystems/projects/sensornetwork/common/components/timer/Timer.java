package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Timer.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class Timer extends Thread {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The timeout. */
	private int timeout;

	/** The holder. */
	private TimerHolder holder;

	/**
	 * Instantiates a new timer.
	 *
	 * @param timeout the timeout
	 * @param holder the holder
	 */
	public Timer(int timeout, TimerHolder holder) {
		this.timeout = timeout;
		this.holder = holder;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		logger.debug("Timer started");
		
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			logger.debug("Timer interrupted");
			return;
		}
		logger.debug("Timer finished");
		synchronized (holder) {
			holder.notify();
			logger.debug("Timer notified holder");
		}
	}
}
