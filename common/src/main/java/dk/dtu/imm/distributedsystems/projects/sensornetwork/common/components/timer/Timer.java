package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer;

/**
 * The Class Timer.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class Timer extends Thread {

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

		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			return;
		}
		
		synchronized (holder) {
			holder.notify();
		}
	}
}
