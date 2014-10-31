package cn.easy.server;

import java.io.File;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import cn.easy.util.FileUtil;
import cn.easy.util.NetworkUtil;
import cn.easy.util.PathUtil;

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

		deleteSessionData();

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
		persistSession(webAppContext);

		server.setHandler(webAppContext);
		changeClassLoader(webAppContext);

		if (scanInternalSeconds > 0) {
			// 启动定时检测class文件是否更新
			Scanner scanner = new Scanner(PathUtil.getRootClassPath(),
					scanInternalSeconds) {

				@Override
				protected void onChange() {
					try {
						System.out.println("-->Loading changed");
						webAppContext.stop();
						EasyClassLoader classLoader = new EasyClassLoader(
								webAppContext, getClassPath());
						webAppContext.setClassLoader(classLoader);
						webAppContext.start();
						System.out.println("-->Reloading complete");
					} catch (Exception e) {
						System.err.println("-->Reloading classes error");
						e.printStackTrace();
					}
				}

			};
			scanner.start();
			System.out.println(String.format(
					"-->Start scanner at interval of %d seconds",
					scanInternalSeconds));
		}

		try {
			System.out.println(String.format(
					"-->Starting web server on port %d", port));
			server.start();
			System.out.println("-->Starting complete");
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteSessionData() {
		try {
			FileUtil.delete(new File(getStoreDir()));
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("resource")
	private void changeClassLoader(WebAppContext webApp) {
		try {
			String classPath = getClassPath();
			EasyClassLoader wacl = new EasyClassLoader(webApp, classPath);
			wacl.addClassPath(classPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void persistSession(WebAppContext webAppContext) {
		String storeDir = getStoreDir();

		SessionManager sm = webAppContext.getSessionHandler()
				.getSessionManager();
		if (sm instanceof HashSessionManager) {
			((HashSessionManager) sm).setStoreDirectory(new File(storeDir));
			return;
		}

		HashSessionManager hsm = new HashSessionManager();
		hsm.setStoreDirectory(new File(storeDir));
		SessionHandler sh = new SessionHandler();
		sh.setSessionManager(hsm);
		webAppContext.setSessionHandler(sh);
	}

	private String getStoreDir() {
		String storeDir = PathUtil.getRootClassPath() + "/../../session_data"
				+ contextPath;
		if ("\\".equals(File.separator))
			storeDir.replace("/", "\\\\");
		return storeDir;
	}

	private String getClassPath() {
		return System.getProperty("java.class.path");
	}

}
