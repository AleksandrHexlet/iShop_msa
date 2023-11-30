package com.sprng.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprng.library.exception.IshopResponseException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Entity
@Table(name = "oauth2_registered_client")
public class ClientRegisterData {
    @Id
    @JsonIgnore
    private String id;

    @Column(nullable = false)
    private String clientId;
    @JsonIgnore
    @Column(nullable = false)
    private ZonedDateTime clientIdIssuedAt = ZonedDateTime.now();

    private ZonedDateTime clientSecretExpiresAt;

    @JsonIgnore
    private String clientSecret;
    private String clientSecretNotSecured;

    @Column(nullable = false)
    private String clientName;
    @JsonIgnore
    @Column(nullable = false, length = 1000)
    private String clientAuthenticationMethods;
    @JsonIgnore
    @Column(nullable = false, length = 1000)
    private String authorizationGrantTypes;

    @Column(length = 1000)
    private String redirectUris;

    @Column(length = 1000)
    private String postLogoutRedirectUris;

    @Column(nullable = false, length = 1000)
    private String scopes;
    @JsonIgnore
    @Column(nullable = false, length = 2000)
    private String clientSettings;
    @JsonIgnore
    @Column(nullable = false, length = 2000)
    private String tokenSettings;

    protected ClientRegisterData() {
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public ZonedDateTime getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public ZonedDateTime getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getClientSecretNotSecured() {
        return clientSecretNotSecured;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    public String getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public String getPostLogoutRedirectUris() {
        return postLogoutRedirectUris;
    }

    public String getScopes() {
        return scopes;
    }

    public String getClientSettings() {
        return clientSettings;
    }

    public String getTokenSettings() {
        return tokenSettings;
    }

    public static class Builder {
        private String id;
        private String clientId;
        private ZonedDateTime clientSecretExpiresAt;
        private String clientSecret;
        private String clientSecretNotSecured;
        private String clientName;
        private Set<String> clientAuthenticationMethods;
        private Set<String> authorizationGrantTypes;
        private Set<String> redirectUris;
        private Set<String> postLogoutRedirectUris;
        private Set<String> scopes;
        private Map<String, Object> clientSettings;
        private Map<String, Object> tokenSettings;
        private StringBuilder stringBuilder = new StringBuilder();
        private ObjectMapper stringMapper = new ObjectMapper();

        public Builder(String clientName) {
            this.clientName = Objects.requireNonNull(clientName, "clientName can not be null");
            stringMapper = stringMapper.registerModule(new JavaTimeModule());

        }

        public Builder id(String id) {
            this.id = Objects.requireNonNull(id, "id can not be null");
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = Objects.requireNonNull(clientId, "clientId can not be null");
            return this;
        }

        public Builder clientSecretExpiresAt(ZonedDateTime clientSecretExpiresAt) {
            this.clientSecretExpiresAt = clientSecretExpiresAt;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }
        public Builder clientSecretNotSecured(String clientSecretNotSecured) {
            this.clientSecretNotSecured = clientSecretNotSecured;
            return this;
        }

        public Builder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public Builder clientAuthenticationMethods(Set<String> clientAuthenticationMethods) {
            this.clientAuthenticationMethods = Objects.requireNonNull(clientAuthenticationMethods, "clientAuthenticationMethods can not be null");
            return this;
        }

        public Builder authorizationGrantTypes(Set<String> authorizationGrantTypes) {
            this.authorizationGrantTypes = Objects.requireNonNull(authorizationGrantTypes, "authorizationGrantTypes can not be null");
            return this;
        }

        public Builder redirectUris(Set<String> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }

        public Builder postLogoutRedirectUris(Set<String> postLogoutRedirectUris) {
            this.postLogoutRedirectUris = postLogoutRedirectUris;
            return this;
        }

        public Builder scopes(Set<String> scopes) {
            this.scopes = Objects.requireNonNull(scopes, "scopes can not be null");
            return this;
        }

        public Builder clientSettings(Map<String, Object> clientSettings) {
            this.clientSettings = Objects.requireNonNull(clientSettings, "clientSettings can not be null");
            return this;
        }

        public Builder tokenSettings(Map<String, Object> tokenSettings) {
            this.tokenSettings = Objects.requireNonNull(tokenSettings, "tokenSettings can not be null");
            return this;
        }

        public ClientRegisterData build() throws IshopResponseException {
            Optional<Builder> builder = Optional.of(this)
                    .filter((builder1 -> builder1.id != null))
                    .filter((builder1 -> builder1.clientId != null))
                    .filter((builder1 -> builder1.clientAuthenticationMethods != null))
                    .filter((builder1 -> builder1.clientSettings != null))
                    .filter((builder1 -> builder1.authorizationGrantTypes != null))
                    .filter((builder1 -> builder1.tokenSettings != null))
                    .filter((builder1 -> builder1.scopes != null));
            if ((builder.isEmpty())) throw new IshopResponseException("fields can not be null");
//            if(id == null || clientId == null || clientAuthenticationMethods == null ||
//                    authorizationGrantTypes == null || scopes == null ||
//                    clientSettings == null || tokenSettings == null  )
//                throw new  IllegalArgumentException("fields can not be null");
            ClientRegisterData clientRegisterData = new ClientRegisterData();

            clientRegisterData.id = this.id;
            clientRegisterData.clientId = this.clientId;
            clientRegisterData.clientSecret = this.clientSecret;
            clientRegisterData.clientSecretNotSecured = this.clientSecretNotSecured;
            clientRegisterData.clientSecretExpiresAt = this.clientSecretExpiresAt;
            clientRegisterData.clientName = this.clientName;
            clientRegisterData.clientAuthenticationMethods = convertSet(this.clientAuthenticationMethods);
            clientRegisterData.authorizationGrantTypes = convertSet(this.authorizationGrantTypes);
            clientRegisterData.redirectUris = convertSet(this.redirectUris);
            clientRegisterData.postLogoutRedirectUris = convertSet(this.postLogoutRedirectUris);
            clientRegisterData.scopes = convertSet(this.scopes);
            clientRegisterData.clientSettings = convertMap(this.clientSettings);
            clientRegisterData.tokenSettings = convertMap(this.tokenSettings);

            return clientRegisterData;
        }

        private String convertSet(Set<String> setStr) {
            stringBuilder.setLength(0);
            setStr.forEach((string) -> stringBuilder.append(string).append(","));
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
//               String string = String.join(",", setStr);
        }

        ;
        private String convertMap(Map<String, Object> mapStr) {
            if(mapStr == null) throw new IllegalArgumentException("mapStr can not be null");
            mapStr.put("@class", "java.util.Collections$UnmodifiableMap");

            try {
                return stringMapper.writeValueAsString(mapStr);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
