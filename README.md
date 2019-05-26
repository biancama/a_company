# A Company

## Scope description


Command line based role playing game. 

A hero (character) will move in a village overran by enemies. Defeat all your enemies in epic battles


Features:
* Create a character. 
* Explore a village (for simplicity the village is squared)
* In the village some enemies are placed. The character will fight against them. 
* Create/Resume a game

General scope:

At the beginning a character is created with 3 weapons, the same for the enemies. The total number of weapons are 10.
The village is a rectangle divided in cells. The character starts from the top-left cell and it can move only inside the village.
Enemies are placed randomly.
When a character ends up in a cell with an enemy, it will fight against it. If the character wins then it will collect all enemy's weapons.
The winner in a fight is the entity who collect more points. 
During a fight points are assigned.
For each entity's weapon:
* If only an entity has that weapon, then that entity gains 1 point
* If both entities have the weapon then a coin is tossed. Who wins will gain 2 points.

if we have a tie, another coin is tossed

The game's objective is either destroy all enemies or collect 10 weapons.

## Use cases

### Create a Game/Character
### Description
As PO I want to be able to create a game for starting playing
### Acceptance criteria
The system will ask some questions to the users:
* number of rows and columns of the village (r >=2 and c>=2)
* number of enemies (n) 

As result, the system will create on memory a village (r x c), (n) enemies each of them with 3 weapons chosen randomly. It will place randomly the enemies in the village (except top left cell).
A character is created with 3 weapons chosen randomly. The character is placed at the top left cell
 
 
### Move a character around the village (Explore and gain experience )
### Description
As PO I want to be able to move my character around the village, so I can find and fight enemies.
### Acceptance criteria
The user can move top, bottom, left, and right. If the user will enter an invalid move then it will be notified by the system. 
If the user will end up in a cell occupied by one or more enemies there it will be a fight.
During a fight points are assigned.
For each entity's weapon:
* If only an entity has that weapon, then that entity gains 1 point
* If both entities have the weapon then a coin is tossed. Who wins will gain 2 points.

if we have a tie, another coin is tossed

If the user wins the fight will remain in the same cell but it will gain all enemy's weapons, otherwise the game ends.

The system will show a simple representation of the village


### Save a game
### Description
As PO I want to be able to save my game so I can keep playing between days 
### Acceptance criteria
The system will save a file in the folder ```games``` with all attributes needed for continuing a game

### Resume a game
### Description
As PO I want to be able to resume my game so I can keep playing between days 
### Acceptance criteria
The system will check all files in the folder ```games``` . The system will re-start the games at the status saved.

## Technical solution
The engine of the game is a finite state machine with all possible commands of the game. Each transiton of the finite state machine will be dispatched to a controller.
The model is stored in memory and it will be a singleton. The view part is a utility class and according with the model will display a simple user interface



## Note
This code is only for assignment purpose. I used design pattern because requested (maybe sometime is overkill). 
I used java 8, because it wasn't specified. I did not use unmodifiable collection because part of java 10.
I neglected for time reason: proper validation, proper dispatcher

