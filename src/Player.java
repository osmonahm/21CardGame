/**
  * Class Player is a component of the model of the BlackJack game, which is used to create
  * a player of the game. The player can deal cards, bet credits, reset its cards, and
  * further methods that return the state of its current stats.
  *
  * @author Osmon
  */

public class Player
{
    public static final int MAX_POINTS = 21;
    public static final int TEN_CREDITS = 10;
    public static final int TWENTY_CREDITS = 20;
    public static final int FIFTY_CREDITS = 50;
    public static final int ONE_HUNDRED_CREDITS = 100;
    public static final int TWO_HUNDRED_CREDITS = 200;
    public static final int FIVE_HUNDRED_CREDITS = 500;

    Card[] cards = new Card[MAX_POINTS + 10];
    private int points = 0;     // keeps track of the player cards points
    public int count = 0;       // keeps track of the count of the player's cards
    private int credit = 1000;  // keeps track of the player's credit

    // receives cards from the deck of cards passed by the parameter and increases points
    private void receiveCard( CardDeck d )
    {
        cards[count] = d.newCard();

        if( cards[count].getCount() == 1 && points <= 10 )
        {
            points += 11;
        }
        else if( cards[count].getCount() == 1 && points > 10)
        {
            points += 1;
        }
        else if( cards[count].getCount() >= 2 && cards[count].getCount() <= 10 )
        {
            points += cards[count].getCount();
        }
        else if( cards[count].getCount() > 10 && cards[count].getCount() <= 13 )
        {
            points += 10;
        }

        count++;
    }

    // resets points to zero
    public void resetPoints()
    {
        points = 0;
    }

    // returns the current points
    public int getPoints()
    {
        return points;
    }

    // returns the current credit
    public int getCredit()
    {
        return credit;
    }

    // increases the credit
    public void increaseCredit( int val )
    {
        System.out.println( "credit " + credit );
        credit += val;
    }

    // bets credit out of the players credit
    public void bet( int nr )
    {
        credit -= nr;
    }

    // returns the player cards
    public Card[] getCards()
    {
        return cards;
    }

    // deals the cards based on the turn
    public void dealCards( CardDeck d )
    {
        if( points == 0 )
        {
            receiveCard( d );
            receiveCard( d );
        }
        else
        {
            receiveCard( d );
        }
    }

    // resets the cards
    public void resetCards()
    {
        for( int i = 0; i < cards.length; i++ )
        {
            cards[i] = null;
        }
    }
}
