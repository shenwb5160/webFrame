package jetty;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class jettyWebStart {

	/** port */
	private int port = 8399;

	protected String charset = "UTF-8";

	public jettyWebStart() {

	}

	public jettyWebStart(int port) {
		this.port = port;
	}

	public jettyWebStart(int port, String charset) {
		super();
		this.port = port;
		this.charset = charset;
	}

	/**
	 * 服务器启动。
	 */
	public void start() {

		Server server = new Server();
		Connector connector = new SelectChannelConnector();

		connector.setPort(this.port);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/bsoft-web-frame");
		webAppContext.setResourceBase("./src/main/webapp");

		server.setHandler(webAppContext);

		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		jettyWebStart jettyWebStarter = new jettyWebStart();
		jettyWebStarter.start();
	}
}
