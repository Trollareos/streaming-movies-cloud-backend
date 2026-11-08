@echo off

set IMAGE_NAME=streamingmovies-api:1.0
set NETWORK_NAME=movies-network
set VOLUME_NAME=movies-postgres-data

echo Building REST API Docker image...
docker build -t %IMAGE_NAME% .
if %ERRORLEVEL% neq 0 (
    echo Docker image build failed.
    exit /b 1
)

docker network inspect %NETWORK_NAME% >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Creating Docker network...
    docker network create %NETWORK_NAME%
)

docker volume inspect %VOLUME_NAME% >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Creating PostgreSQL volume...
    docker volume create %VOLUME_NAME%
)

echo Setup completed successfully.
