/**
  * Class CardDeck is a component of the model of the BlackJack game, which is used to create a deck of cards
  * consisting of four suits, thirteen cards per each suit. It also consists of other methods that help
  * manipulate the cards in the deck.
  *
  * @author Osmon
  */

public class CardDeck
{
    private int cardCount;  // keeps track of the cards count
    private Card[] deck = new Card[ 4 * Card.SIZE_OF_ONE_SUIT ];    // creates an array of cards objects

    // Constructor creates 52 cards of four suits
    public CardDeck()
    {
        createSuit( Card.SPADES );
        createSuit( Card.HEARTS );
        createSuit( Card.CLUBS );
        createSuit( Card.DIAMONDS );
    }

    // newCard takes a random card, if there are any, out of the deck and returns it
    public Card newCard()
    {
        Card nextCard = null;

        if( cardCount == 0 )
        {
            System.out.println( "CardDeck Error: no more cards" );
        }
        else
        {
            int index = ( int )( Math.random() * cardCount );
            nextCard = deck[index];

            for( int i = index + 1; i < cardCount; i++ )
            {
                deck[i-1] = deck[i];
            }

            cardCount--;
        }

        return nextCard;
    }

    // checks if there are more cards
    public boolean moreCards()
    {
        return ( cardCount > 0 );
    }

    // creates 12 cards of a certain suit
    private void createSuit( String whichSuit )
    {
        for( int i = 1; i <= Card.SIZE_OF_ONE_SUIT; i++ )
        {
            deck[cardCount] = new Card( whichSuit, i );
            cardCount++;
        }
    }
}
