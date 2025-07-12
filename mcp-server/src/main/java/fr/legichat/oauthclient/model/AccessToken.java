package eu.legichat.oauthclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessToken (
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        int expiresIn,

        String scope
        ) {
        public boolean isExpiredSoon() {
                // TODO : à améliorer !
                return true;
        }
}
