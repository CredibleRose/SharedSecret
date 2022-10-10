package org.enigma;

import org.enigma.data.db.secret.SecretDao;
import org.enigma.data.db.secret.SecretDataSourceImpl;
import org.enigma.domain.secret.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SecretDao.class).to(SecretDao.class);
        bind(SecretDataSourceImpl.class).to(SecretDataSource.class);
    }
}
