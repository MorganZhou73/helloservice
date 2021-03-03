# For Java 8
#FROM openjdk:8-jdk-alpine

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
	        unzip && \
      mkdir /jmeter \
      && cd /jmeter/ \
      && wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-$JMETER_VERSION.tgz \
      && tar -xzf apache-jmeter-$JMETER_VERSION.tgz \
      && rm apache-jmeter-$JMETER_VERSION.tgz

# ADD all the plugins
#ADD jmeter-plugins/lib /jmeter/apache-jmeter-$JMETER_VERSION/lib

# Install
RUN cd  /jmeter/apache-jmeter-$JMETER_VERSION \
    && wget -P lib/ext/  --trust-server-names https://jmeter-plugins.org/get/  \
    && wget -O lib/cmdrunner-2.2.jar  http://search.maven.org/remotecontent?filepath=kg/apc/cmdrunner/2.2/cmdrunner-2.2.jar \
#        && mv ./lib/ext/remotecontent*jmeter-plugins-manager-1.3.jar ./lib/ext/jmeter-plugins-manager-1.3.jar \
        && java -cp ./lib/ext/jmeter-plugins-manager-*.jar org.jmeterplugins.repository.PluginManagerCMDInstaller \
        && ./bin/PluginsManagerCMD.sh install jpgc-ffw

# Set the working directory to /qa
RUN mkdir -p /qa
WORKDIR /qa

# Copy the current directory contents into the container at /qa
COPY entrypoint.sh /qa/

RUN chmod +x /qa/entrypoint.sh

#RUN chmod 555 /qa/entrypoint.sh
