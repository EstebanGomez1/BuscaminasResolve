all: buscaminas

buscaminas: main.o
	g++ -g main.o -o buscaminas

main.o: main.cpp
	g++ -g -c main.cpp

.PHONY: clean
	rm *.o buscaminas