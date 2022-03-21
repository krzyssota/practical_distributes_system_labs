FROM gcc:11
COPY cpp/score.sh /userdata/score.sh
COPY examples /userdata/examples
RUN gcc -o userapp
CMD ["./userapp"]