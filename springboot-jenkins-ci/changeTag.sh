#!/bin/bash
sed "s/tagVersion/$1/g" k8s/pods.yml > node-app-pod.yml