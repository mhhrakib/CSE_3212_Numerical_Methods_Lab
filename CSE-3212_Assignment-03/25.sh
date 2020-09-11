#!/bin/sh
read -p "Enter Problem No. " roll
if test $roll -eq 1; then
cd Problem1
echo "Modify input.txt and save it the in gedit window that is opened."
echo "if you are done with modification enter 1".
gedit input.txt
read num
if test $num -eq 1; then
javac Problem1.java
java Problem1
echo "output file is opened in gedit".
echo "Enter CTRL + C to exit.".
gedit output.txt
fi
fi

if test $roll -eq 2; then
cd Problem2
echo "Modify input.txt and save it the in gedit window that is opened."
echo "if you are done with modification enter 1".
gedit input.txt
read num
if test $num -eq 1; then
javac Problem2.java
java Problem2
echo "output file is opened in gedit".
gedit output.txt
echo "Enter CTRL + C to exit.".
fi
fi

