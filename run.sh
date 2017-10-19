#!/bin/bash
consumer_key=NYKqVs1GaepwSRgWcI1erIQtf
consumer_key_secret=DUvsAQz5fNNNfGwlpTTV4Iub2FdxRQnr6W6AzJ8BrTkCHt1anC
access_token_key=16752622-SNrD4IdS4bA2VU4FvYEr008kI1CietpNySxJgJ1tn
access_token_secret=CKzF4mZcFrwyTGqzLq3KHqRqQWOhsMpaPk1oyOOAVsNxL
storm jar storm-twitter-0.0.1-SNAPSHOT.jar fr.pvillard.storm.topology.Topology cluster $consumer_key $consumer_key_secret $access_token_key $access_token_secret > storm_twitter.log 2>&1 &
