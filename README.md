# ElevatorChallenge
 
Ich habe mich für die Variante Entschieden, dass der Aufzug nur zwischen zwei Stockwerken verkehren kann. Das ganze habe ich dann mittels Threads für die Anfragen realisiert. 

##How to run
Die Applikation ist eine normale Java Konsolen Anwendung, also in der Umgebung der Wahl importieren und starten. 

##How it works
Um unterschiedliche Stockwerke zu testen, einfach den Befehl "controller.addRequest(0,7);" benutzen. Dadurch wird im ElevatorController eine Request erzeugt und direkt in die Liste eingefügt. Diese Liste wird dann  vom ExecutorService bearbeitet samt der Liste der verfügbaren Lifte mit einer lambda Funktion. Ich habe mich entschieden den Ablauf zu simulieren. So berrechne ich die Zeit, die ein Aufzug benötigt, mittels der Differenz der Stockwerke und wandle sie dann in Millisekunden um. Den erzeugten Wert übergebe ich dann dem Thread für die sleep() Funktion. Wenn die Zeit dann abgelaufen ist, gilt es quasi, dass der Lift das Ziel erreicht hat und er wird wieder in die Liste der verfügbaren Lifte gegeben.

Um etwas flexibler zu sein habe ich für die Klassen Interfaces erstellt.
 