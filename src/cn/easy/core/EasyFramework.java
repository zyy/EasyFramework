package cn.easy.core;

import cn.easy.server.IServer;
import cn.easy.server.ServerFactory;

public class EasyFramework {
	private static IServer server;

	public static void main(String[] args) {
		if (args == null || args.length <= 0) {
			server = ServerFactory.getServer();
			server.start();
		}
	}

}
