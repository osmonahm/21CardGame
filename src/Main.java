/**
  * Class Main is a component of the controller of the BlackJack game, which is used to create instances
  * of the three main components of the game and link them together.
  *
  * @author Osmon
  */
public class Main
{
    public static void main( String[] args )
    {
        CardDeck deck1 = new CardDeck();    // first deck of cards
        CardDeck deck2 = new CardDeck();    // seconds deck of cards
        Player player1 = new Player();      // first player
        Player player2 = new Player();      // second player
        GameWriter writer = new GameWriter();   // the game GUI

        GameController gc = new GameController( deck1, deck2, player1, player2, writer );   // Controller initialized by the other components
    }
}