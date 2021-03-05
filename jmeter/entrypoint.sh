#!/bin/bash
set -x
# start jmeter test

#for jmx in $(ls Functional)
#do
    jmeter -n -t /qa/greetingTest.jmx -l /qa/greetingTest.jtl
#done

# in order to check the log files from bash later, sleep long time so keep the pod in Running status
sleep 9000
