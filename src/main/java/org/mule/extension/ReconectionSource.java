package org.mule.extension;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.scheduler.Scheduler;
import org.mule.runtime.api.scheduler.SchedulerService;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;

import javax.inject.Inject;

@MediaType("text/plain")
public class ReconectionSource extends Source<String, Void>{

    @Connection
    ConnectionProvider<BasicConnection> connectionProvider;

    @Inject
    SchedulerService schedulerService;

    private Scheduler scheduler;

    @Override
    public void onStart(SourceCallback<String, Void> sourceCallback) throws MuleException {
        BasicConnection connect;
        try {
           connect = connectionProvider.connect();
            scheduler = schedulerService.ioScheduler();
            scheduler.execute(() -> {
                boolean shouldFinish = false;
                while (!shouldFinish) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        shouldFinish = true;
                    }
                    if(BasicConnectionProvider.fail){
                        sourceCallback.onConnectionException(new ConnectionException(new RuntimeException(), connect));
                        shouldFinish = true;
                    } else {
                        sourceCallback.handle(Result.<String, Void>builder().output("Value").build());
                    }
                }
            });
        } catch (Exception e) {
            sourceCallback.onConnectionException(new ConnectionException(e));
        }
    }

    @Override
    public void onStop() {
        if(scheduler != null){
            scheduler.stop();
        }
    }
}
