#https://restful-api.dev/

curl http://localhost:8081/single-object/1

curl -X "POST" http://localhost:8081/single-object -H "Content-Type: application/json" -H "Accept: application/json" -d "{ \"name\": \"Apple MacBook Pro 16\", \"data\": { \"year\": 2019, \"price\": 1849.99, \"CPU model\": \"Intel Core i9\", \"Hard disk size\": \"1 TB\"}}"