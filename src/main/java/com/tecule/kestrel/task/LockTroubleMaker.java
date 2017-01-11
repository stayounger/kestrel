package com.tecule.kestrel.task;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@DependsOn("applicationContextProvder")
public class LockTroubleMaker {
	private ThreadPoolTaskExecutor executor;

	@Autowired
	FooThread fooThread;

	@PostConstruct
	public void init() {
		executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setKeepAliveSeconds(300);
		executor.initialize();

		executor.execute(fooThread);

		for (int t = 0; t < 9; t++) {
			try {
				DataThread thread = new DataThread();
				executor.execute(thread);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@PreDestroy
	public void destroy() {
		executor.shutdown();
	}
}
