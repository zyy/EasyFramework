package cn.easy.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import cn.easy.util.NetworkUtil;

public class JettyServer implements IServer {
	private Server server;
	private int port;
	private String webAppDir;
	private WebAppContext webAppContext;
	private boolean isRunning;
	private int scanInternalSeconds;
	private String contextPath;

	public JettyServer(int port, String webAppDir, String contextPath,
			int scanInternalSeconds) {
		super();
		this.port = port;
		this.webAppDir = webAppDir;
		this.scanInternalSeconds = scanInternalSeconds;
		this.contextPath = contextPath;
	}

	public synchronized void start() {
		if (!isRunning)
			doStart();
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	private void doStart() {
		if (!NetworkUtil.isPortAvilable(port))
			throw new IllegalStateException(String.format(
					"port %s is already in use", port));
		System.out.println("-->Starting EasyFramework ");
		server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		server.addConnector(connector);
		webAppContext = new WebAppContext();
		webAppContext.setContextPath(contextPath);
		webAppContext.setResourceBase(webAppDir);
		webAppContext.setInitParameter(
				"org.eclipse.jetty.servlet.Default.dirAllowed", "false");
		webAppContext.setInitParameter(
				"org.eclipse.jetty.servlet.Default.useFileMappedBuffer",
				"false");

		if (scanInternalSeconds > 0) {
			// TODO 启动定时检测class文件是否更新
		}

		try {
			System.out.println(String.format("-->Starting web server on port %d", port));
			server.start();
			System.out.println("-->Starting complete");
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
