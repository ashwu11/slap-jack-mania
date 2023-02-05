# Slap Jack Mania

### A reaction-based card game


This application represents a game of Slap Jack, a multiplayer reaction game of 
up to four players. Players can either continue as a guest or create an account.
If an account is created, you can view each saved account's 
total wins, games played, as well as a streak count of consecutive wins.
The flips and slaps will be represented by certain keys, depending on how many 
players are in the game. The rules below apply to all players, and a 
clarification of the terms can be found below as well.
There will also be a timer option for the desired duration of a game.

This project is of interest to me because Slap Jack is one of my favorite card 
games. In fact, my friends and I have developed new rules a long time ago for 
a harder version because the normal Slap Jack was too easy. I call this Mania mode, 
and I am familiar with the rules as I have played many times. If I am able to 
implement the normal mode as desired, I would attempt to add a Mania mode as well.
I have always wanted to code some sort of interactive game, and this project is the 
perfect opportunity to do so. I believe this will be a fun project to work on.

## Rules

- **Objective:** to get rid of your cards.
- Last player to slap must collect all cards from current round.
- Players who slap incorrectly must collect all cards from current round.
- There is no penalty if all players do not slap the pile.
- In a clockwise order, players count up (from one to king) every time a card is 
flipped. The count starts over when a round ends.


- Slap on Jacks
- Slap when the card value matches the current count
- Slap on Doubles
- Slap on Sandwiches


### Terms

- `Double:` Two cards with the same value. Examples:
    - 3 + 3
    - Queen + Queen


- `Sandwich:` Two cards of the same value with any card in between. Examples:
    - 1 + 7 + 1
    - King + 3 + King

 

## User Stories
- As a user, I want to be able to create a new account and add it to a list of 
accounts
- As a user, I want to be able to view the list of accounts with their wins,
games played, and win streak.
- As a user, I want to be able to slap a card.
- As a user, I want to be able to remove (i.e. flip) and add cards to a player's 
deck.
- As a user, I want to be able to add a timer to a game.