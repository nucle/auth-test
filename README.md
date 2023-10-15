# Install postgresql:
Execute this command from root project
````shell
docker compose up -d  
````

### Start application
Liquibase will create anything for you

### Test with in memory (Default)
Go to DefaultSecurityConfig and comment out the lines below:
````java
    //@Bean
    //public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate template, RegisteredClientRepository registeredClientRepository) {
    //    return new JdbcOAuth2AuthorizationService(template, registeredClientRepository);
    //}
````

### Test with database
Go to DefaultSecurityConfig and uncomment the lines below:
````java
    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate template, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(template, registeredClientRepository);
    }
````

# Login:
PKCE Generator:
https://developer.pingidentity.com/en/tools/pkce-code-generator.html

### Open in browser and login
``
User: user1
``
``
Password: password
``
````
http://localhost:9009/oauth2/authorize?
response_type=code
&client_id=messaging-client
&scope=openid
&redirect_uri=http://localhost:8080
&code_challenge=t3RzPLjc8NiO_mpmIuTJYi2P6Ps8y9jP6cphOhSNozw
&code_challenge_method=S256
````

### GET Token -> Replace code from first call
````
POST http://localhost:9009/oauth2/token?client_id=messaging-client
&redirect_uri=http://localhost:8080
&grant_type=authorization_code
&scope=openid
&code=vuQU-CV2pTgRud8tJpBwehskC4Ef5FXemRyh6oiW0gzgiyx6ka-o5SiFvw_e_crnwtgpIpknJFc7MgLfizAgIbiCJma5onH5J6Bj3NRcChtjya5nWiA13UMIrRGhnct-
&code_verifier=mlZDBO_BNru6SRVHWhhAMJIsVCAdVZAL8VRTpy8hXBzcOJ92gKHtXPI3Agb0FF-l5NtYIbAcrl7bsHqSVJNDVWa0sjSoe0RvdwNy4Yz6StaQ-vd7uU0IRg-1tshE19PA

> {%
client.global.set("AUTH_TOKEN",response.body.access_token);
client.global.set("ID_TOKEN",response.body.id_token);
%}
>
````
### Response:
```json
{
  "access_token": "eyJraWQiOiJlNDY4N2Y1My1kNTY0LTQ4OTEtOTUwNS1iNjU0YjFhYzUxODEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2OTczNTY3MDIsInNjb3BlIjpbIm9wZW5pZCJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDkiLCJleHAiOjE2OTczNTcwMDIsImlhdCI6MTY5NzM1NjcwMn0.k5410MtCRsNoMFQ9J7wk4qMjSgbvOHauD1CVlFfyCHNebnl5YrtRtYQvDj0Wbz-OHS_sZl7O2-Uvu0SbWuYJ5CHnf8eHY0cPkfCx9VM2bi7MOAlHRunX9kmNR-GZSC5gkx61HmOV93HNsVRhElXN5Ep-2I5y04yVYk1fkwqpZUJdyQRsLIbn9COY_22VzYlC4J_gu_CK8PbKgMuFp3XByxMjuKIFyNivWNySbZjP5KysXUnESo3n-ivrry9S7WmrY_GCV9cM8GFH-JnJK3CEF6E2Bf8bwWwTBtMrZTuoPP8_WPAKA72wS061xKweyxw-fznqSaiPieQdAJeSKxJA5Q",
  "scope": "openid",
  "id_token": "eyJraWQiOiJlNDY4N2Y1My1kNTY0LTQ4OTEtOTUwNS1iNjU0YjFhYzUxODEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJhenAiOiJtZXNzYWdpbmctY2xpZW50IiwiYXV0aF90aW1lIjoxNjk3MzU2MTEwLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDkiLCJleHAiOjE2OTczNTg1MDIsImlhdCI6MTY5NzM1NjcwMiwic2lkIjoiX1NselFFdFpPdG5kYVp1T25ubjNJSkhLRVdtRWpIRG5ac0ozS2Z1RUlVUSJ9.S4ZPUTp7uYgaJ89EdX8Q9QUPfqzTRQOgUJTQniLJ5E5Ld3rd3qGM6jJdooeDBO_v43Yt5x6D-C9b9qyI9IphZgosAuHS4KVCahb7xV4_sDt0emDEZHv6nRyh1DHwFftOhHE2e5sKHZwhy4crk8Isms376GvUMtKk1dvZml8L2ZdRQky5m5fenKC2qW9i5PwbN9eykhjESq2cxC7vzrY6OWNfNcjk0n2Vm4F85w54w6Nl9hoxY7w-vp2n_pX0OGLpjeUA9EtVgH4gPi_AKp8D17u69HDif9PA_fJ3t16Gi3GxuFrhKGfW7Si9xOD0nrj12TiXzjfeJVFqUzRlU0aKCw",
  "token_type": "Bearer",
  "expires_in": 299
}
```

Open https://jwt.io/ and copy paste id_token

With in memory: "sid": "_SlzQEtZOtndaZuOnnn3IJHKEWmEjHDnZsJ3KfuEIUQ"
With db: missing
