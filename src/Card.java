/**
  * Class Card is a component of the model of the BlackJack game, which is used to create cards,
  * which are then used by the CardDeck class. It also contains methods that help the CardDeck
  * class work with the cards in the deck.
  *
  * @author Osmon
  */

public class Card
{
    public static final String SPADES = "Spades";
    public static final String HEARTS = "Hearts";
    public static final String DIAMONDS = "Diamonds";
    public static final String CLUBS = "Clubs";

    public static final int ACE = 1;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;

    public static final int SIZE_OF_ONE_SUIT = 13;

    private String suit;    // the suit of the card
    private int count;      // the count of the card

    // Constructor initializes cards properties
    public Card( String s, int c )
    {
        suit = s;
        count = c;
    }

    // returns the suit of the card
    public String getSuit()
    {
        return suit;
    }

    // returns the count of the card
    public int getCount()
    {
        return count;
    }
}