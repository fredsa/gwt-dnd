#!/bin/bash
#
set -uev
rm -rf .gwt-cache www tomcat
./*-compile.cmd
mod=$( ls www )
scp -r www/$mod/std sauer@allen-sauer.com:$mod
ssh sauer@allen-sauer.com ./deploy.sh $mod
