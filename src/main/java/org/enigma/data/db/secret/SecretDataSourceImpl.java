package org.enigma.data.db.secret;

import jakarta.inject.Inject;
import org.enigma.domain.secret.Secret;
import org.enigma.domain.secret.SecretDataSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SecretDataSourceImpl implements SecretDataSource {

    private final SecretDao secretDao;

    @Inject
    SecretDataSourceImpl(SecretDao secretDao) {
        this.secretDao = secretDao;
    }


    @Override
    public void putSecret(Secret secret) {
        secret.setUuid(UUID.randomUUID());
        secretDao.putSecret(toDb(secret));
    }

    @Override
    public Secret getSecretById(Map<String, String> uuidAndPass) {
        return toDomain(secretDao.getSecretById(uuidAndPass));
    }

    private List<Secret> toDomain(List<SecretEntity> param) {
        List<Secret> result = new java.util.ArrayList<>(Collections.emptyList());

        param.forEach(secretEntity -> {
                    result.add(new Secret(
                            secretEntity.getUuid(),
                            secretEntity.getEncryptedSecret(),
                            secretEntity.getPassword(),
                            secretEntity.getTimeToBurn(),
                            secretEntity.getShowAndBurn(),
                            secretEntity.getNumberOfReads(),
                            secretEntity.getNeedPassword()
                    ));
                }
        );

        return result;
    }

    private Secret toDomain(SecretEntity secretEntity) {
        return new Secret(
                secretEntity.getUuid(),
                secretEntity.getEncryptedSecret(),
                secretEntity.getPassword(),
                secretEntity.getTimeToBurn(),
                secretEntity.getShowAndBurn(),
                secretEntity.getNumberOfReads(),
                secretEntity.getNeedPassword()
        );
    }

    private SecretEntity toDb(Secret secret) {
        return new SecretEntity(
                secret.getUuid(),
                secret.getEncryptedSecret(),
                secret.getPassword(),
                secret.getTimeToBurn(),
                secret.getShowAndBurn(),
                secret.getNumberOfReads(),
                secret.getNeedPassword()
        );
    }
}
