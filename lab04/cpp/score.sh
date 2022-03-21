#!/bin/bash
g++ -x c++ -

./a.out

find examples -name *.in |
while read example_file
do
	example_number=$(basename -s.in ${example_file})
	./a.out < ${example_file} > out
	exit_code=$?
	diff out "examples/${example_number}.out"
	diff_code=$?

	echp "${exit_code}, ${diff_code}"
done