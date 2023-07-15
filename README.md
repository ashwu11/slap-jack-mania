# Slap Jack Mania
### A reaction-based card game


This application represents a game of Slap Jack, a multiplayer reaction game of 
up to four players. Players can choose whether to save a game, which will add
their account to the leaderboard. Players can also view each saved account's 
total wins and games played. The 'flips' and 'slaps' will be represented by certain keys, 
depending on how many players are in the game. The rules below apply to all players, 
and a clarification of the terms can be found below as well.

This project is of interest to me because Slap Jack is one of my favorite card 
games. I have always wanted to code some sort of interactive game, and this project is the 
perfect opportunity to do so. I believe this will be a fun project to work on.

The point system of the game follows an opponent pairing process.
The pair with the first player to slap correctly becomes the active pair for that round.
The slower player in the active pair must collect all cards from the current round.
The cards will be distributed based on the actions of the 
following pairs:
- Player 1 VS Player 2 
- Player 3 VS Player 4



# Rules
### General
- **Objective:** to get rid of all your cards.
- Last player to slap in the active pair must collect all cards from the current round.
- Players who slap incorrectly must collect all cards from the current round.
- There is no penalty if all players do not slap the pile.
- In ascending order, players count from Ace to King every time a card is 
flipped. The count starts over when a round ends.

### When to slap
- On Jacks
- The card value matches the current count
- Doubles: Two cards with the same value. Examples:
    - 3 + 3
    - Queen + Queen
- Sandwiches: Two cards of the same value with any card in between. Examples:
    - 1 + 7 + 1
    - King + 3 + King


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



## Phase 4: Task 2
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



## Phase 4: Task 3
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