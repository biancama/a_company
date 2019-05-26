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
* number of enemies (n) default 3

As result, the system will create on memory a village (r x c), (n) enemies each of them with1 weapons chosen randomly. It will place randomly the enemies in the village (except top left cell).
A character is created with 1 weapon chosen randomly. The character is placed at the top left cell
 
 
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
The engine of the game is a finite state machine with all possible commands of the game. Each transition of the finite state machine will be dispatched to a consumer.
The model is stored in memory and it will be a singleton. The view part is a utility class and according with the model will display a simple user interface


## The build 
Compiling and test
```mvn clean package -DskipTests```

tests
```mvn verify```


running 
```java -jar target/game-1.0-SNAPSHOT.jar```

You can save the game only after the start game

You can resume a game only from a file saved before


I added also docker support
Create docker container

```mvn docker:build```

to run the container

```docker run -v -d --name a_company com.example.company/game:1.0-SNAPSHOT```

Interact with the conatiner

```docker run  -a stdin -a stdout -i -t --name a_company com.example.company/game:1.0-SNAPSHOT```

## Note
Design pattern used:
Observable, singleton, create factory. 

Simple Ioc using service autowire annotation


This code is only for assignment purpose. I used design pattern because requested (maybe sometime is overkill). 
I used java 8, because it wasn't specified. I did not use unmodifiable collection because part of java 10.
I neglected for time reason: proper validation, proper dispatcher

I neglected ioc for multiple service of the same type. Despite ioc accept more than one bean implementing the same interface, I didn't implement qualifier.

I used Observer and newInstance feature because it will be deprecated in java 9.

Inversion of control of the framework is very primitive. I did it only for the sake of exercise. I'm wiring GameStateMachine but a proper wire would be using another annotation for example @Bean
I wanted to decouple listener to standard output... allowing to use view different than simple standard output

I neglected integration tests