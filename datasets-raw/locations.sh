#!/bin/bash

gawk -F, 'NR==1 || !a[$1]++;' populations.csv | gawk -F, '{print $1}' > locations.txt
