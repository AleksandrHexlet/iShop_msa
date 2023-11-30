package com.sprng.users.entity;

import com.sprng.library.exception.IshopResponseException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;


public class ClientAppRegistration  {
    @Size(min = 2, max = 199,  message = "Длина имени владельца приложения должна быть равен или более 2 символам и менее 199 символов")

    private String ownerUserName;
    private String ownerPassword;

    private String redirectUris;
    private String scopes;
    private String postLogoutRedirectUris;
    private String clientAppName;
    private int rateAppId;


    public static class Builder {
        private String ownerUserName;
        private String ownerPassword;

        private Set<String> redirectUris;
        private Set<String> scopes;
        private Set<String> postLogoutRedirectUris;
        private String clientAppName;
        @Positive
        private int rateAppId;


        public Builder ownerUserName(String ownerUserName) {
            this.ownerUserName = ownerUserName;
            return this;
        }

        public Builder setOwnerPassword(String ownerPassword) {
            this.ownerPassword = ownerPassword;
            return this;
        }

        public Builder setRedirectUris(Set<String> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }

        public Builder setScopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder setPostLogoutRedirectUris(Set<String> postLogoutRedirectUris) {
            this.postLogoutRedirectUris = postLogoutRedirectUris;
            return this;
        }

        public Builder setClientAppName(String clientAppName) {
            this.clientAppName = clientAppName;
            return this;
        }

        public Builder setRateAppId(int rateAppId) {
            this.rateAppId = rateAppId;
            return this;
        }

        public ClientAppRegistration build() throws IshopResponseException {
            Optional<Builder>builder = Optional.of(this)
                    .filter((builderItem -> builderItem.ownerUserName != null ))
                    .filter((builderItem -> builderItem.ownerPassword != null ))
                    .filter((builderItem -> builderItem.redirectUris != null ))
                    .filter((builderItem -> builderItem.scopes != null ))
                    .filter((builderItem -> builderItem.postLogoutRedirectUris != null ))
                    .filter((builderItem -> builderItem.clientAppName != null ));

            if(builder.isEmpty()) throw  new IshopResponseException("Для регистрации необходимо заполнить все поля владельца приложения");
            ClientAppRegistration clientAppOwnerRegistration = new ClientAppRegistration();

            clientAppOwnerRegistration.clientAppName = this.clientAppName;
            clientAppOwnerRegistration.ownerUserName = this.ownerUserName;
            clientAppOwnerRegistration.ownerPassword = this.ownerPassword;
            clientAppOwnerRegistration.rateAppId = this.rateAppId;
            clientAppOwnerRegistration.scopes = convertSet(this.scopes);
            clientAppOwnerRegistration.postLogoutRedirectUris = convertSet(this.postLogoutRedirectUris);
            clientAppOwnerRegistration.redirectUris = convertSet(this.redirectUris);

        return clientAppOwnerRegistration;
        };
        private String convertSet(Set<String> setStr) {
            String stringFromSet = String.join(",", setStr);
            return stringFromSet;
//
        }
    }

}



