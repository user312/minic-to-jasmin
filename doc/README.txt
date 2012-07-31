"Implementazione di un compilatore
per il linguaggio MiniC che generi
istruzioni per la Java Virtual Machine."
--------------------------------------------
Progetto di Compilatori e interpreti
A.A. 2011/2012

Prof.ssa V. Carchiolo
--------------------------------------------
Gruppo: Alessandro Nicolosi, Riccardo Pulvirenti, Giuseppe Ravid�
--------------------------------------------

Contenuto della cartella:
=========================
- minic-to-jasmin: la cartella contente i files di progetto ed il codice sorgente.
- doc: la cartella contenente la documentazione (relazione, consegna, ecc). 
- readme.txt: questo file.

Installazione:
==============
Per far partire il progetto bisogna intanto installare il compilatore Jasmin scaricandolo
dal sito http://sourceforge.net/projects/jasmin/files/
Una volta estratto il contenuto del pacchetto, bisogna modificare i files run.sh e gui.sh
sostituendo il percorso di "JASMIN_PATH" con il percorso al quale si trova il file jasmin.jar.

Lanciare l'applicazione:
========================
E' possibile lanciare l'applicazione in modalit� testuale o grafica.

Linux/MacOs: 
 
 - Modalit� testuale
	Aprire il terminale e posizionarsi al percorso ".../minic-to-jasmin/". 
	Modificare i permessi dei files run.sh e gui.sh con il comando "chmod 777 run.sh" 
	e "chmod 777 gui.sh". Scrivere da terminale "./run.sh examples/<dir>/<file.c>" 
	dove <dir> rappresenta una delle cartelle contenente gli esempi e <file.c> 
	il relativo file.c da compilare.
 
 - Modalit� grafica:
	Aprire il terminale e posizionarsi al percorso ".../minic-to-jasmin/". 
	Modificare i permessi dei files run.sh e gui.sh con il comando "chmod 777 run.sh" 
	e "chmod 777 gui.sh". Immetere da terminale il comando "./run.sh".
	
Windows:
 
 - Modalit� testuale
	Aprire la shell dos e posizionarsi al percorso ".../minic-to-jasmin/bin/". 
	Immettere il comando "java m2j.Main ../examples/<dir>/<file.c>" 
	dove <dir> rappresenta una delle cartelle contenente gli esempi e <file.c> 
	il relativo file.c da compilare.
 
 - Modalit� grafica:
	Aprire la shell dos e posizionarsi al percorso ".../minic-to-jasmin/bin/". 
	Immettere il comando "java m2j.Main".
