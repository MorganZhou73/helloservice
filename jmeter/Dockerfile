# Use Java 8 slim JRE
FROM openjdk:8-jre-slim


ENTRYPOINT entrypoint.sh
# JMeter version
ARG JMETER_VERSION=5.3
# Set JMeter Home
ENV JMETER_HOME /jmeter/apache-jmeter-$JMETER_VERSION/

ENV QA_HOME /qa

# Add JMeter to the Path
ENV PATH $JMETER_HOME/bin:$PATH:$QA_HOME:$PATH
# Install few utilities
RUN apt-get clean && \
    apt-get update && \
    apt-get -qy install \
                wget \
                vim \
                curl \
	        unzip && \
      mkdir /jmeter \
      && cd /jmeter/ \
      && wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-$JMETER_VERSION.tgz \
      && tar -xzf apache-jmeter-$JMETER_VERSION.tgz \
      && rm apache-jmeter-$JMETER_VERSION.tgz

# ADD all the plugins
#ADD jmeter-plugins/lib /jmeter/apache-jmeter-$JMETER_VERSION/lib

#Install kubectl
RUN  /usr/bin/curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl \
     && chmod +x ./kubectl  \
     &&  mv ./kubectl /usr/local/bin/kubectl

# Set the working directory to /qa
RUN mkdir -p /qa
WORKDIR /qa

# Copy the current directory contents into the container at /qa
COPY entrypoint.sh /qa/
COPY greetingTest.jmx /qa/

RUN chmod +x /qa/entrypoint.sh
