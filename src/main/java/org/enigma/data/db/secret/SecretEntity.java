package org.enigma.data.db.secret;

import java.util.UUID;

public class SecretEntity {
    private UUID uuid;
    private String encryptedSecret;
    private String password;
    private String timeToBurn;
    private Boolean showAndBurn;
    private Boolean needPassword;
    private int numberOfReads;

    SecretEntity(UUID uuid, String encryptedSecret, String password, String timeToBurn, Boolean showAndBurn, int numberOfReads, Boolean needPassword) {
        this.uuid = uuid;
        this.encryptedSecret = encryptedSecret;
        this.password = password;
        this.timeToBurn = timeToBurn;
        this.showAndBurn = showAndBurn;
        this.numberOfReads = numberOfReads;
        this.needPassword = needPassword;
    }

    public UUID getUuid() { return uuid; }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEncryptedSecret() { return encryptedSecret; }

    public void setEncryptedSecret(String encryptedSecret) {
        this.encryptedSecret = encryptedSecret;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeToBurn() {
        return timeToBurn;
    }

    public void setTimeToBurn(String timeToBurn) {
        this.timeToBurn = timeToBurn;
    }

    public Boolean getShowAndBurn() {
        return showAndBurn;
    }

    public void setShowAndBurn(Boolean showAndBurn) {
        this.showAndBurn = showAndBurn;
    }
    public Boolean getNeedPassword() {
        return needPassword;
    }

    public void setNeedPassword(Boolean needPassword) {
        this.needPassword = needPassword;
    }

    public int getNumberOfReads() {
        return numberOfReads;
    }

    public void setNumberOfReads(int numberOfReads) {
        this.numberOfReads = numberOfReads;
    }
}
