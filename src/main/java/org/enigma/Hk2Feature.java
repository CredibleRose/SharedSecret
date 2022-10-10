package org.enigma;

import org.glassfish.hk2.api.PostConstruct;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;
import org.enigma.app.SecretResource;
import org.enigma.data.db.secret.SchedulerExecutorServiceSecret;
import org.glassfish.hk2.api.PreDestroy;
import java.io.IOException;


@Provider
public class Hk2Feature implements Feature, PostConstruct, PreDestroy {
    @Override
    public boolean configure(FeatureContext context) {
        context.register(SecretResource.class);
        context.register(new ApplicationBinder());
        return true;
    }
    @Override
    public void postConstruct() {
        try {
            SchedulerExecutorServiceSecret.startScheduler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void preDestroy() {
        try {
            SchedulerExecutorServiceSecret.stopScheduler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

