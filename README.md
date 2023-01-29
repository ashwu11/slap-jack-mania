# Slap Jack Mania

### A reaction-based card game


This application represents a game of Slap Jack, 
with a `Normal mode` and a `Mania mode`. These modes will consist of different rules, 
and flips and slaps will be represented by certain keys
(depending on how many players are in the game). The rules below labelled with an asterisk
are adjustable in the sense that they may be turned off if desired.

Up to four players can participate in this multiplayer game.
Players can either continue as a guest or create an account.
There will be an option to save your account, and there will be a billboard to
accumulate each account's total wins, games played, as well as a streak count
of consecutive wins. There will also be a timer option for the desired duration of a game.

This project is of interest to me because Slap Jack is one of my favorite card games.
In fact, my friends and I have developed new rules a long time ago for a harder version 
because the normal Slap Jack was too easy. I call this Mania mode, and I am familiar with the 
rules as I have played many times. I have always wanted to code some sort of interactive game,
and this project is the perfect opportunity to do so. 
I believe this will be a fun project to work on.

## Rules

### Normal Mode
- Objective is to get rid of cards.
- Last player to slap must collect all the cards from current round.
- Players who slap incorrectly must collect the cards too.
- In a clockwise order, players count up (from one to king) every time a card is flipped.
  The count starts over when a round ends.


- Slap on Jacks
- Slap when the card matches the current count
- *Slap on doubles
- *Slap on Sandwiches


### Mania Mode
- Objective is to collect cards.
- First person to slap may collect the cards from that round.
- Players who slap incorrectly will lose a single card to the bottom of the pile.
- There is no count and players must ***not*** slap on Jacks.


- Slap on decks 
- *Slap on doubles
- *Slap on sandwiches
- *Slap on couples 



### Terms

- `Double:` Two cards with the same value. Examples:
    - 3 + 3
    - Queen + Queen


- `Sandwich:` Two cards of the same value with any card in between. Examples:
    - 1 + 7 + 1
    - King + 3 + King

    
- `Deck:` Two cards that add up to ten, face cards have a value of one. Examples:
  - 2 + 8
  - 9 + Queen


- `Couple:` the specific combination of royal face cards. The only two combinations are:
    - Queen + King
    - King + Queen


## User Stories
- As a user, I want to be able to create a new account and add it to a list of accounts
- As a user, I want to be able to view the list of accounts with their wins,
games played, and win streak.
- As a user, I want to be able to flip and slap a card
- As a user, I want to be able to turn off a rule on the rules page
- As a user, I want to be able to add a timer to the game