## Start docker keycloak

```
docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:23.0.4 start-dev
```

## Mongodb commands

```
sudo systemctl stop mongod
sudo mongod --dbpath programming/DAC/db/

```