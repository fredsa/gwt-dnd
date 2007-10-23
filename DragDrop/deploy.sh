#!/bin/bash
#
set -uev
rm -rf .gwt-cache www tomcat
./*-compile.cmd
scp -r www/* sauer@allen-sauer.com:
ssh sauer@allen-sauer.com ./deploy.sh com.allen_sauer.gwt.dragdrop.demo.DragDropDemo
