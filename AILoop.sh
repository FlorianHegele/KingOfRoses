#!/bin/bash

P1=0
P2=0
DRAW=0
i=0

./launch.sh run

while [ $i -le 200 ]
do
    ./launch.sh runo 
    case $? in
        1)
            let "P2 = P2 + 1"; echo $P2
            ;;
        0)
            let "P1 = P1 + 1"; echo $P1
            ;;
        255)
            let "DRAW = DRAW + 1"; echo $DRAW
    esac
    ((i++))
done

echo "Player 1 won : $P1"
echo "Player 2 won : $P2"
echo "DRAW : $DRAW"

