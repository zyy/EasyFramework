package cn.easy.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class NetworkUtil {
	public final static int MIN_PORT_NUMBER = 1;
	public final static int MAX_PORT_NUMBER = 65535;

	public static boolean isPortAvilable(int port) {
		if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER)
			return false;
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}
}
