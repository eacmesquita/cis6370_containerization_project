This repository is forked from https://github.com/veracode/verademo, a deliberately insecure application.
This project wll refactor the Docker practices to build a more secure image. The image will be scanned using an open-source tool to understand the security improvements.

Dockerfile_insecure is the original Dockerfile.

Dockerfile_secured is a Dockerfile that first builds the application and then copies over only the war file to a Java runtime image.
Additionally, the code has been updated to avoid hardcoded databases passwords, and instead looks for a password mounted through Docker volumes. SQL injection attacks have also been suppressed.

A docker compose file was added to spin up the applications with mounted credentials

docker volume create db
docker volume create db_creds

cp ../db/*.sql /var/lib/docker/volumes/db/_data/ 
echo -n $password > /var/lib/docker/volumes/db_creds/_data/password


and then starting the applications with docker-compose up -d