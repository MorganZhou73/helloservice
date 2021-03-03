#!/bin/bash
set -x
# start jmeter test

#for jmx in $(ls Functional)
#do
    jmeter -n -t /qa/greetingTest.jmx -l /qa/greetingTest.jtl
#done
