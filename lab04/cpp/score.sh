#!/bin/bash

g++ -Wall -x c++ -

EXAMPLES_PATH="examples/${TASK_ID}"

find ${EXAMPLES_PATH} -name *.in -exec basename -s .in {} \: | \
while read test
do
	./a.out < ${EXAMPLES_PATH}/${test} > out
	exit_code=$?
	diff out "${EXAMPLES_PATH}/${test}.out" > /dev/null
	diff_code=$?

	echo "${exit_code}, ${diff_code}"
done

# docker build -t cpp-builder -f Dockerfile.cpp
#docker run -e TASK_ID=

git rm --cached lab04/lab04_venv/