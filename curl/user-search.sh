#!/bin/bash
curl -i \
-H "Content-Type: application/json" \
-H "X-HTTP-Method-Override: GET" \
http://localhost:8080/users/search -d '["1","2"]'
