<div align="center">

# MicroLink 🔗


### A simple URL shortening service.

</div>

## Running the service locally

### Pre-requisites
1. Docker daemon running locally (perhaps through Docker Desktop)
2. Port `8080` free

### Running the service
1. Clone repo
2. CD into root directory
3. Create a `.env` file with the required environment variables outlined in Spring application properties file
4. Create an `application-local.properties` file. Copy and paste contents from prod file.
5. Ensure Spring profile is set to `local` in compose file
6. Build and run the server with `docker-compose up --build`

### Winding the service down
After terminating the docker image, be sure to clean up all resources created by docker by running `docker-compose down`.
