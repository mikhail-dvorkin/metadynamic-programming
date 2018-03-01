@echo off
cd ..
del /s *.class > nul
call javac ru\ifmo\np\GadgetFinder.java
call java -Xms920M -Xmx920M ru.ifmo.np.GadgetFinder
