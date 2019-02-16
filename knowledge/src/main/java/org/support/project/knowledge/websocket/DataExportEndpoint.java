package org.support.project.knowledge.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.arnx.jsonic.JSONException;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.bat.CreateExportDataBat;
import org.support.project.web.websocket.CallBatchEndpoint;
import org.support.project.web.websocket.EndpointConfigurator;


@ServerEndpoint(value = "/exporting", configurator=EndpointConfigurator.class)
public class DataExportEndpoint extends CallBatchEndpoint {
	/** ログ */
	private static Log LOG = LogFactory.getLog(DataExportEndpoint.class);
	
	@OnOpen
	public void onOpen(Session session) throws IOException {
		if (super.isAdmin(session)) {
			call(session, CreateExportDataBat.class);
		}
	}

	@OnClose
	public void onClose(Session session) {
	}

	@OnMessage
	public void onMessage(String text) throws JSONException, IOException {
	}
	
	@OnError
	public void onError(Throwable t) {
		LOG.warn("websocket on error." + t.getClass().getName() + " : " + t.getMessage());
		if (LOG.isDebugEnabled()) {
			LOG.warn("websocket error -> ", t);
		}
	}
}
