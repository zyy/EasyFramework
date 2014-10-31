/**
 * Copyright (c) 2011-2014, yycoder 692895299@qq.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.easy.server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.easy.util.StringUtil;

public abstract class Scanner {
	private Timer timer;
	private TimerTask timerTask;
	private File rootDir;
	private int scanInterval;
	private boolean isRunning;

	private Map<String, TimeSize> preScan = new HashMap<String, TimeSize>();
	private Map<String, TimeSize> curScan = new HashMap<String, TimeSize>();

	public Scanner(String rootDir, int scanInterval) {
		if (StringUtil.isEmpty(rootDir))
			throw new IllegalArgumentException("The rootDir can not be empty");
		this.rootDir = new File(rootDir);
		if (!this.rootDir.isDirectory())
			throw new IllegalArgumentException(String.format(
					"The rootDir [%s] is not exist", rootDir));
		if (scanInterval <= 0)
			throw new IllegalArgumentException(
					"The parameter scanInterval must be than more zero");
		this.scanInterval = scanInterval;
	}

	protected abstract void onChange();

	private void working() {
		scan(rootDir);
		compare();

		preScan.clear();
		preScan.putAll(curScan);
		curScan.clear();
	}

	private void compare() {
		if (preScan.size() == 0)
			return;

		if (!preScan.equals(curScan))
			onChange();
	}

	private void scan(File file) {
		if (file == null || !file.exists())
			return;

		if (file.isFile()) {
			try {
				curScan.put(file.getCanonicalPath(),
						new TimeSize(file.lastModified(), file.length()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				scan(f);
			}
		}
	}

	public void start() {
		if (!isRunning) {
			timer = new Timer("Easy-Scanner");
			timerTask = new TimerTask() {

				@Override
				public void run() {
					working();
				}
			};
			timer.schedule(timerTask, 1010L * scanInterval,
					1010L * scanInterval);
			isRunning = true;
		}
	}

	public void stop() {
		if (isRunning) {
			timer.cancel();
			timerTask.cancel();
			isRunning = false;
		}
	}

}

class TimeSize {
	private long time;
	private long size;

	public TimeSize(long time, long size) {
		super();
		this.time = time;
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSize other = (TimeSize) obj;
		if (size != other.size)
			return false;
		if (time != other.time)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("[time = %d, size = %d]", time, size);
	}

}
