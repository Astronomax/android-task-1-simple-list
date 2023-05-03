# This Dockerfile creates a static build image for CI

FROM openjdk:11-jdk

# Just matched `app/build.gradle`
ARG ANDROID_COMPILE_SDK="32"
ENV ANDROID_COMPILE_SDK=${ANDROID_COMPILE_SDK}
# Just matched `app/build.gradle`
# It should match buildToolsVersion.
ARG ANDROID_BUILD_TOOLS="32.0.0"
ENV ANDROID_BUILD_TOOLS=${ANDROID_BUILD_TOOLS}
# It's what version of the command line tools we're going to download from the official site.
# Official Site-> https://developer.android.com/studio/index.html
# There, look down below at the cli tools only, sdk tools package is of format:
#        commandlinetools-os_type-ANDROID_SDK_TOOLS_latest.zip
# when the script was last modified for latest compileSdkVersion, it was which is written down below
ARG ANDROID_SDK_TOOLS="8512546"

ARG ANDROID_HOME=/android-sdk-linux
ENV ANDROID_HOME=${ANDROID_HOME}
ENV PATH="${PATH}:/android-sdk-linux/platform-tools/"

# install OS packages
RUN apt-get --quiet update --yes
RUN apt-get --quiet install --yes wget apt-utils tar unzip lib32stdc++6 lib32z1 build-essential ruby ruby-dev
# We use this for xxd hex->binary
RUN apt-get --quiet install --yes vim-common
# Create a new directory at specified location
RUN install -d ${ANDROID_HOME}
# Here we are installing androidSDK tools from official source,
# (the key thing here is the url from where you are downloading these sdk tool for command line, so please do note this url pattern there and here as well)
# after that unzipping those tools and
# then running a series of SDK manager commands to install necessary android SDK packages that'll allow the app to build
RUN wget --output-document=$ANDROID_HOME/cmdline-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_TOOLS}_latest.zip
# move to the archive at ANDROID_HOME
RUN unzip -d $ANDROID_HOME/cmdline-tools $ANDROID_HOME/cmdline-tools.zip
# since commandline tools version 7583922 the root folder is named "cmdline-tools" so we rename it if necessary
RUN mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/tools || true

ENV PATH="${PATH}:${ANDROID_HOME}/cmdline-tools/tools/bin/"

# Nothing fancy here, just checking sdkManager version
RUN sdkmanager --version

# use yes to accept all licenses
RUN yes | sdkmanager --licenses || true
RUN sdkmanager "platforms;android-${ANDROID_COMPILE_SDK}"
RUN sdkmanager "platform-tools"
RUN sdkmanager "build-tools;${ANDROID_BUILD_TOOLS}"

# install Android SDK
#RUN echo y | android-sdk-linux/tools/android --silent update sdk --no-ui --all --filter extra-android-m2repository
#RUN echo y | android-sdk-linux/tools/android --silent update sdk --no-ui --all --filter extra-google-google_play_services
#RUN echo y | android-sdk-linux/tools/android --silent update sdk --no-ui --all --filter extra-google-m2repository

# install FastLane
COPY Gemfile.lock .
COPY Gemfile .
RUN gem install bundler -v 1.16.6
RUN bundle install
