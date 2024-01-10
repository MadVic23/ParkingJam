# PARKING-JAM


**DONE BY:**

-   Darío Elguezabal Selioutin, 190024
-   Álvaro García Mosquera, 200110
-   Salma Pérez Upierre, 200210
-   Victor Valdeolmos Rabadán, 200109





The files read by the application are .txt that are used to store the levels. Each time the user pass a level, the application will read the next level with the distribution of the level. More levels can be added. This levels are stored in src/main/resources/levels. The name format for this .txt files is "level_"+number of the level+".txt". If you want to add a new level you must take into account that trucks and cars don't have the same identifiers. If you want to add cars to the level they must have an id from letter 'a' to 'r' and if you want to add trucks to the level they must have an id from letter 's' to 'z'. This is an example of level_1.txt.

    Level 1
    8 8
    ++++++++
    +aa   u+
    +s  t u+
    +s**t u@
    +s  t  +
    +b   cc+
    +b vvv +
    ++++++++
 




Also, the application is going to read and write in a .bin file whenever the user wants to load or save their game. This saved game is stored in src/main/resources/saved

To play with this application you only need to use the mouse with actions like click and drag to move and to use the elements of the hud you will only need to click on the buttons.
