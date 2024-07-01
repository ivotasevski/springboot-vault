Spring Boot application that demonstrates reading secrets from Hashicorp Vault and usage in application.

## Vault
The vault is already preconfigured with some secrets. However, on every start/restart the vault must be unsealed by providing any 3 (out of 5) keys. The keys can be found in "keys.txt" file.  
In order to login to vault and check the existing secrets go to `http://localhost:8200`. Choose token as auth method and provide the root token that can be found in "keys.txt".

## Demo
On application startup, the app will connect to Vault and will retrieve the specified secrets. There are 2 properties (bootsrtap.yml) that define which secrets will be injected:
`spring.cloud.vault.kv.backend - name of the secret store`
`spring.cloud.vault.kv.application-name - the context of the secret store where secrets to be read from`

For example, if we have:  
spring.cloud.vault.kv.backend="mysecrets"  
spring.cloud.vault.kv.application-name="first" 

In order to see the secrets that were injected perform GET http://localhost:8080/props

on startup the application will load secrets from the following path: secretes/mysecrets/first.  
Note that the path must resolve to actual secret collection (JSON) and must not be an interim path (in matter of path hierarchy).



 