This repository is forked from https://github.com/veracode/verademo, a deliberately insecure application.
This project wll refactor the Docker practices to build a more secure image. The image will be scanned using an open-source tool to understand the security improvements.

Dockerfile_secured is a Dockerfile that first builds the application and then copies over only the war file to a Java runtime image.