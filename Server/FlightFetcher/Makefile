# Compilation
compile:
	mkdir -p bin
	javac -cp "src:lib/*" -d bin src/FlightFetcher.java

# Execution avec parametres
run:
	java -cp "bin:lib/*" FlightFetcher $(ARGS)

# Nettoyage
clean:
	rm -rf bin
