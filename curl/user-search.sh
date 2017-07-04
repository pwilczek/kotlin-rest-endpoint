#!/bin/bash
curl -i \
-H "Content-Type: application/json" \
-H "X-HTTP-Method-Override: GET" \
http://localhost:8080/users/search -d '["d42ce7d3-8b89-4db0-9da1-f229a62412c7","342c1da7-40ae-4e6d-8934-c0bdb41a251e"]'
