package model;

    /**
     * Represents a player in a game with a name, cards in hand, and slap and flip buttons
     **/

public class Player {
    private String name;
    private CardDeck hand;
    private int numCardsLeft;
    private String slap;
    private String flip;

    public Player(String name, CardDeck cards, String slap, String flip) {
        // takes user input for the slap and flip button!
    }

    // REQUIRES: player hand is not empty
    // TODO method that returns the top card from player's hand
    public void flipCard() {  // change void to Card
        // occurs if button pressed = flip
        // returns the top card from the player's hand, and removes it from the player's hand

        // numCardsLeft -= 1
        // return hand.get(numCardsLeft); this fails to compile :((
        // hand.remove(numCardsLeft)
    }

    //TODO method that adds cards in pile to a player's deck
    public void getCards(CardDeck cards) {  // TODO Q: not sure if this is how I should set the parameter
        // parameter takes in a list of cards (that'll be the cards in pile)
        // adds these cards to the players deck
    }


    //EFFECTS: checks if the player had no cards left
    public Boolean checkEmpty() {
        return numCardsLeft == 0;
    }
}
