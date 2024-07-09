FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y git maven && apt-get clean

WORKDIR /app

# Clone the repository from the dev branch
RUN git clone --branch dev git@github.com:yuriy-tsarenko/trustnow-backend.git .
