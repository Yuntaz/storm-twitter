#!/bin/bash
consumer_key=G3eWwtWnJ6x4P4idv7JrVoMEb
consumer_key_secret=NIfPmW32MyGoOVHWt8jdTkWqzIET9rmAK2uw7bgXNYw1W0KZoj
access_token_key=16752622-jwSm2gmWsNrWQ4WpVYwTRxbWwqFU4YnPqeAtGBqJ4
access_token_secret=4oLjsBTgnzHJEdwkGAsJgC0mR4EeZi3YPnEyjFPReJe0p
storm jar storm-twitter-0.0.3-SNAPSHOT fr.pvillard.storm.topology.Topology cluster $consumer_key $consumer_key_secret $access_token_key $access_token_secret > log_storm_twitter.log 2>&1 &
