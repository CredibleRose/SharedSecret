package org.enigma.domain.secret;

import java.util.List;
import java.util.Map;

public interface SecretDataSource {

    Secret getSecretById(Map<String, String> uuidAndPass);

    void putSecret(Secret secret);
}
