# Slap Jack Mania ♠️❤️♣️♦️
### A multiplayer reaction game based on the card game, Slap Jack 

A fully shuffled deck of cards is split evenly among up to four players, 
who take turns revealing their cards while counting from Ace to King. 
When the pattern of cards satisfies a certain rule, players must race to use the 'slap' action represented 
by a key on the keyboard, with the goal of being faster than other players.
The slowest player takes all cards from the current round, and the winner is 
the first player to get rid of all their cards. Players interact with the game through 'flips' and 'slaps' 
that are represented by unique keys on the keyboard.
After a game, players may save their name to the leaderboard, where they can
view all players' total wins and number of games played. 
They can also retrieve game data stored from the previous session. <br><br>

## Rules
### General
- **Objective:** to get rid of all your cards
- Last player to slap must collect all cards from the current round
- Players who slap incorrectly must collect all cards from the current round
- There is no penalty if **all** players miss a slap
- In ascending order, players count from Ace to King every time a card is
flipped. The count starts over when a round ends
- Winner is determined by whoever gets rid of all their cards first

### When to slap
- On Jacks
- The card value (i.e. the number) matches the current count
- Doubles: Two cards with the same value. Examples:
    - 3 + 3
    - Queen + Queen
- Sandwiches: Two cards of the same value with any card in between. Examples:
    - 1 + 7 + 1
    - King + 3 + King
 
### User Controls
| Player  | Slap | Flip |
|---------|------|------|
|Player 1 | B    | M    |
|Player 2 | Z    | C    |
|Player 3 | 2    | A    |
|Player 4 | L    | 0    |

<br><br>
## Design
<img width="1195" alt="Screenshot 2023-10-09 at 1 31 26 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/4fed0abf-c5e6-4a4f-b397-a3969ea39118">

| Enter Players | End Screen | Leaderboard |
| ------------- | ---------- | ----------- |
|<img width="1195" alt="Screenshot 2023-10-09 at 1 33 50 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/1c20e9e6-a144-4fa4-86f0-98d20adb94d1"> | <img width="1195" alt="Screenshot 2023-10-09 at 1 35 36 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/02c8ea14-b89f-4b2a-bede-78c90d63c061"> | <img width="1195" alt="Screenshot 2023-10-09 at 1 35 58 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/dbb82f6f-3186-48d6-9193-ea32c9f74e2c"> |

### Game Flow
| | | |
|-|-|-|
| <img width="1195" alt="Screenshot 2023-10-09 at 1 34 41 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/4c393e05-ea17-462d-97a7-bed5f725362e"> | <img width="1195" alt="Screenshot 2023-10-09 at 1 34 51 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/142f0b4f-51bc-464f-b2fb-5b2200b67326"> | <img width="1195" alt="Screenshot 2023-10-09 at 1 35 01 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/3bc9532e-bb6b-42c2-8ebb-6b2c28aa1ff9"> | 

| | | |
|-|-|-|
|<img width="1195" alt="Screenshot 2023-10-09 at 1 35 07 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/47f365ee-4810-49ba-8094-1c062878e625"> | <img width="1195" alt="Screenshot 2023-10-09 at 1 35 16 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/c81fc1aa-4ee9-4417-a56c-be7b54b64a73"> | <img width="1195" alt="Screenshot 2023-10-09 at 1 35 24 PM" src="https://github.com/ashwu11/slap-jack-mania/assets/134242218/f78be172-1559-4946-a146-d875d64f6127"> |

<sup>Player names above are used for demonstration purposes only<sup>

<br><br>

## About
This project is of interest to me because Slap Jack is one of my favorite card games. 
I have always wanted to make some sort of interactive game, and this project is the 
perfect opportunity to do so. This was such a rewarding and fun project to work on.
I used Java and core principles of object-oriented programming (OOP) to develop a console application.
From there, I transformed the console game to a graphical user interface, designed with Java Swing, to allow user-friendly interactions.
Furthermore, I used the test-driven approach by writing comprehensive test cases with JUnit to ensure app stability.
The data from previous sessions are stored using JSON serialization and can be accessed in future sessions if desired.

<br><br>

## User Stories
- As a user, I want to be able to add an account to a list of accounts by saving a game.
- As a user, I want to be able to view the list of accounts saved.
- As a user, I want to be able to slap a card.
- As a user, I want to be able to remove (i.e. flip) a card.
- As a user, I want to be able to quit a game.

- As a user, when I finish the game, I want to be given the option to save the leaderboard to file.
- As a user, when I start the application, I want to be given the option to load the leaderboard from file.


## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by pressing the 
  'Save Game' button after a game.
- You can generate the second required action related to adding Xs to a Y by clicking the 
  'Sort By Name' button in the leaderboard panel.
- You can locate my visual component by starting the application.
- You can save the state of my application by clicking the 'Store Data' button when a game ends.
- You can reload the state of my application by clicking the 'Load Game' button when the application starts.


## Example of Data Persistence
Sat Apr 08 00:10:58 PDT 2023
New account added to leaderboard: ivan

Sat Apr 08 00:10:58 PDT 2023
Account updated: ivan

Sat Apr 08 00:10:58 PDT 2023
New account added to leaderboard: kobe

Sat Apr 08 00:10:58 PDT 2023
Account updated: kobe

Sat Apr 08 00:10:58 PDT 2023
Account updated: alan

Sat Apr 08 00:11:01 PDT 2023
Leaderboard has been sorted by name.



## Reflection
If I had more time to work on the project, I would add a method in the CardDeck class that can return 
a list of cards that have been played in the game so far. Doing this will remove the association arrow 
between the Game class and the Card class, which improves cohesion. Instead of keeping track of a list 
of cards throughout the Game class, I would instead delegate that task to CardDeck and make it their 
responsibility since CardDeck must already have an association to the Card class as it represents a 
deck of cards.

Furthermore, the code in the Game class is quite long and many aspects can be refactored to make it 
shorter and more readable. I could extract classes from the Game class which handles displaying 
the user interface for different aspects of the game, then call its methods from the 
Game class. Another example is the duplication of the cardCountInt and cardCount. They should
work in tandem and changes to one must be reflected in the other, so this can be refactored by 
adjusting the code so that it is possible to remove one of these variables. This will reduce 
coupling since it is difficult to maintain both of them at the same time and make sure they are 
aligned with the same Card.Value.

Another design aspect I wish to change is using a hashmap for the Leaderboard class to represent a 
collection of accounts instead of using an array list. I believe this fits the methods in the code 
better and the functionality that I was looking for in that class. For instance, I would be able to
quickly locate an account by their username instead of looping through the list to find it, which 
might be more useful for the class. 
