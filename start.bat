@echo off
setlocal

if not defined DB_PASSWORD (
    echo ERROR: DB_PASSWORD is not set. Set it in your PowerShell session first.
    exit /b 1
)
set "POSTGRES_PASSWORD=%DB_PASSWORD%"


set IMAGE_NAME=streamingmovies-api:1.0
set NETWORK_NAME=movies-network
set VOLUME_NAME=movies-postgres-data
set DB_CONTAINER=movies-postgres
set API_CONTAINER=movies-api

docker network inspect %NETWORK_NAME% >nul 2>&1
if %ERRORLEVEL% neq 0 (
    docker network create %NETWORK_NAME%
)

docker volume inspect %VOLUME_NAME% >nul 2>&1
if %ERRORLEVEL% neq 0 (
    docker volume create %VOLUME_NAME%
)

docker rm -f %API_CONTAINER% >nul 2>&1
docker rm -f %DB_CONTAINER% >nul 2>&1

echo Starting PostgreSQL container...
docker run -d ^
  --name %DB_CONTAINER% ^
  --network %NETWORK_NAME% ^
  -e POSTGRES_DB=moviesdb ^
  -e POSTGRES_USER=moviesuser ^
  -e POSTGRES_PASSWORD ^
  -v %VOLUME_NAME%:/var/lib/postgresql/data ^
  --health-cmd="pg_isready -U moviesuser -d moviesdb" ^
  --health-interval=5s ^
  --health-timeout=5s ^
  --health-retries=10 ^
  postgres:16

echo Waiting for PostgreSQL to become healthy...

:wait_db
for /f "tokens=*" %%i in ('docker inspect --format="{{.State.Health.Status}}" %DB_CONTAINER% 2^>nul') do set DB_HEALTH=%%i

if "%DB_HEALTH%"=="healthy" goto start_api
if "%DB_HEALTH%"=="unhealthy" (
    echo PostgreSQL container is unhealthy.
    exit /b 1
)

timeout /t 2 /nobreak >nul
goto wait_db

:start_api
echo Starting REST API container...
docker run -d ^
  --name %API_CONTAINER% ^
  --network %NETWORK_NAME% ^
  -p 8080:8080 ^
  -e DB_URL=jdbc:postgresql://%DB_CONTAINER%:5432/moviesdb ^
  -e DB_USERNAME=moviesuser ^
  -e DB_PASSWORD ^
  --read-only ^
  --tmpfs /tmp ^
  --cap-drop ALL ^
  --security-opt no-new-privileges ^
  %IMAGE_NAME%

echo Backend started successfully.
echo REST API: http://localhost:8080/api/movies
