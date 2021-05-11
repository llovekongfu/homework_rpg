# homework_rpg
homework for rpg

This is a text-based role-playing game (RPG)-maze treasure hunt.
Players can use the W, S, A, D four buttons on the keyboard to control the treasure hunter to move up, down, left, and right.
The game is divided into 6 levels, the first three levels belong to the non-fog mode, and the last three levels belong to the fog mode. 
The non-fog mode means that the map is completely exposed to the player, and the fog mode means that the player observes the map and 
the location of the treasure at a specified time, and then enters the fog mode.
Players must move the treasure hunter to find the treasure according to the location of the treasure in their memory.
The first level: The player will have 9 minutes to find the treasures scattered on the map, otherwise it will be regarded as a failure.
Level 2: The player will have 6 minutes to find the treasures scattered on the map, otherwise it will be regarded as a failure.
Level 3: The player will have 3 minutes to find the treasures scattered on the map, otherwise it will be regarded as a failure.
The fourth level: Players will have 8 seconds to observe the map and the location of the treasures on the map, and plan the treasure hunt path. 
After 8 seconds, the map will be hidden by the fog. The player will have 5 minutes to find the treasures scattered on the map, otherwise it will be regarded as a failure.
Fifth level: The player will have 6 seconds to observe the map and the location of the treasure on the map, and plan the treasure hunt path. After 6 seconds, the map will 
be hidden by the fog. The player will have 5 minutes to find the treasures scattered on the map, otherwise it will be regarded as a failure.
Level 6: Players will have 4 seconds to observe the map and the location of the treasures on the map, and plan the treasure hunt path. After 4 seconds, the map will be hidden 
by the fog. The player will have 5 minutes to find the treasures scattered on the map, otherwise it will be regarded as a failure.

Code
1. The Dark class is mainly used to generate fog objects.
2. The DataUtil class is mainly used to load the map and configuration information of each level from the json file.
3. The GameLevel class belongs to the bean class, which is used to encapsulate each level information into an object.
4. The Hunter class is mainly used to generate the treasure hunter object and control the location of the treasure hunter.
5. The Main class is the main class for program operation.
6. The MainPanel class is the main panel class, used to load and draw background images.
7. The Map class is mainly used to query the number of treasure chests.
8. The Note class is mainly used to display the remaining number of treasure chests, treasure hunting time and other information.
9. The Position class is mainly used to save and obtain position information.
10. The Treasure class is mainly used to generate treasure chest objects.
11. The Wall class is mainly used to generate wall objects.



