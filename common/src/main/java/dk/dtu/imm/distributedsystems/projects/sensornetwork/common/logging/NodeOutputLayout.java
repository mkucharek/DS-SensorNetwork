package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

public class NodeOutputLayout extends LayoutBase<ILoggingEvent> {

	  public String doLayout(ILoggingEvent event) {
	    StringBuffer sbuf = new StringBuffer(128);
	    sbuf.append(event.getTimeStamp());
	    sbuf.append("");
	    sbuf.append(event.getFormattedMessage());
	    sbuf.append(CoreConstants.LINE_SEPARATOR);
	    return sbuf.toString();
	  }
	}
