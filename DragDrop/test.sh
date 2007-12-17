#!/bin/bash
#
set -uev
rm -rf .gwt-cache www tomcat
./*-compile.cmd
mv www/com.allen_sauer.gwt.dnd.demo.DragAndDropDemo www/dnd
scp -r www/* sauer@allen-sauer.com:
ssh sauer@allen-sauer.com ./deploy.sh dnd
