#!/bin/bash
cd /home/ec2-user/song-microservice

nohup java -jar songMicroservice.jar \
  --stage=prod \
  --region=us-east-1 \
  > app.log 2>&1 &