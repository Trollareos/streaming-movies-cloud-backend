@echo off

set NETWORK_NAME=movies-network
set DB_CONTAINER=movies-postgres
set API_CONTAINER=movies-api

echo Stopping and removing containers...
docker rm -f %API_CONTAINER% >nul 2>&1
docker rm -f %DB_CONTAINER% >nul 2>&1

echo Removing Docker network...
docker network rm %NETWORK_NAME% >nul 2>&1

echo Backend stopped successfully.
echo PostgreSQL volume was kept for data persistence.
