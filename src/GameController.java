import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
  * Class GameController is the main component of the BlackJack game, which is used to connect the
  * other two components that are the model and the view classes. It receives input from the view
  * component, sends it to the model component to manipulate it, and then sends it back to the
  * view component which in turn shows it to the user.
  *
  * @author Osmon
  */

public class GameController implements ActionListener
{
    CardDeck deck1;     // first deck
    CardDeck deck2;     // seconds deck

    Player player1;     // first player
    Player player2;     // second player

    protected GameWriter writer;    // the game writer
    private int currentBet = 0;     // keeps track of the current bet
    private int currentTurn = 0;    // keeps track of the current turn
    private int p1roundsWon = 0;    // keeps track of the player1's rounds won
    private int p2roundsWon = 0;    // keeps track of the player2's rounds won
    private boolean roundEnded = false;

    ArrayList<Card> p1Cards = new ArrayList<>();    // used to store player1's cards
    ArrayList<Card> p2Cards = new ArrayList<>();    // used to store player2's cards
    private int cardsLeft = 104;                    // keeps track of the number of cards left

    public GameController( CardDeck d1, CardDeck d2, Player p1, Player p2, GameWriter w )
    {
        deck1 = d1;
        deck2 = d2;
        player1 = p1;
        player2 = p2;
        writer = w;

        // adding the buttons action listeners
        writer.welcomeScreenButton.addActionListener( this );
        writer.standButton.addActionListener( this );
        writer.hitButton.addActionListener( this );
        writer.dealButton.addActionListener( this );
        writer.withdrawButton.addActionListener( this );
        writer.tenCreditsButton.addActionListener( this );
        writer.twentyCreditsButton.addActionListener( this );
        writer.fiftyCreditsButton.addActionListener( this );
        writer.oneHundredCreditsButton.addActionListener( this );
        writer.twoHundredCreditsButton.addActionListener( this );
        writer.fiveHundredCreditsButton.addActionListener( this );
    }

    // starts the game
    public void playGame()
    {
        try
        {
            Thread.sleep( 1000 );
        }
        catch ( InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }

        askP1ForBet();
    }

    // starts the first round by dealing 2 cards for both players, and shows them in the GUI
    public void firstRound()
    {
        player1.dealCards( whichDeck() );
        player2.dealCards( whichDeck() );

        // Showing player1 cards
        Card[] cards1 = player1.getCards();
        p1Cards.clear();

        for( int i = 0; i < cards1.length; i++ )
        {
            if( cards1[i] != null )
            {
                p1Cards.add( cards1[i] );
            }
        }
        writer.toggleShowP1Cards( true, p1Cards );
        cardsLeft -= p1Cards.size();

        // Showing player2 cards
        Card[] cards2 = player2.getCards();
        p2Cards.clear();

        for( int i = 0; i < cards2.length; i++ )
        {
            if( cards2[i] != null )
            {
                p2Cards.add( cards2[i] );
            }
        }
        writer.toggleShowP2Cards( true, p2Cards );
        cardsLeft -= p2Cards.size();

        writer.toggleP1CardsPointsLabel( true, player1.getPoints() );
        writer.toggleStandButton( true );
        writer.toggleHitButton( true );
        writer.toggleDeckIconLabel( cardsLeft );
    }

    // deals cards for the first player, and shows the updated cards
    private void P1Round()
    {
        player1.dealCards( whichDeck() );

        // Showing player1 cards
        Card[] cards1 = player1.getCards();
        p1Cards.clear();

        for( int i = 0; i < cards1.length; i++ )
        {
            if( cards1[i] != null )
            {
                p1Cards.add( cards1[i] );
            }
        }
        writer.toggleShowP1Cards( true, p1Cards );
        cardsLeft -= 1;
        writer.toggleP1CardsPointsLabel( true, player1.getPoints() );
        writer.toggleStandButton( true );
        writer.toggleHitButton( true );
        writer.toggleDeckIconLabel( cardsLeft );
    }

    // shows cards for the seconds player, and shows the updated cards
    private void P2Round()
    {
        player2.dealCards( whichDeck() );

        // Showing player2 cards
        Card[] cards2 = player2.getCards();
        p2Cards.clear();

        for( int i = 0; i < cards2.length; i++ )
        {
            if( cards2[i] != null )
            {
                p2Cards.add( cards2[i] );
            }
        }
        writer.toggleShowP2Cards( true, p2Cards );
        cardsLeft -= 1;

        writer.toggleStandButton( true );
        writer.toggleHitButton( true );
        writer.toggleDeckIconLabel( cardsLeft );
    }

    // resets cards, changes turns and shows who won
    private void roundOver()
    {
        while( player2.getPoints() <= 16 )
        {
            P2Round();
        }

        writer.toggleStandButton( false );
        writer.toggleHitButton( false );
        writer.toggleP2CardsPointsLabel( true, player2.getPoints() );

        showWhoWon();

        player1.resetPoints();
        player1.resetCards();
        player2.resetPoints();
        player2.resetCards();

        writer.toggleP1CardsPointsLabel( false, player1.getPoints() );
        writer.toggleP2CardsPointsLabel( false, player2.getPoints() );

        currentTurn++;

        /*
        try
        {
            Thread.sleep(1000 );
        }
        catch( InterruptedException ex )
        {
            Thread.currentThread().interrupt();
        }
         */

        if( player1.getCredit() != 0 && player2.getCredit() != 0 )
        {
            if( ( currentTurn % 2 ) == 0 )
            {
                askP1ForBet();
            }
            else
            {
                askP2ForBet();
            }
        }
        else
        {
            gameFinished();
        }
    }

    // asks player 1 for bet
    private void askP1ForBet()
    {
        writer.toggleDealButton();
        writer.toggleWithdrawButton();
        writer.toggleCurrentBetLabel();
        writer.P2CardsPointsLabel.setVisible( false );

        showAvailableChips();
    }

    // shows the available chips
    private void showAvailableChips()
    {
        if( player1.getCredit() != 0 && player2.getCredit() != 0 )
        {
            if ( ( player1.getCredit() >= 500 ) && ( player2.getCredit() >= 500 ) )
            {
                writer.toggleTenCreditsButton(true);
                writer.toggleTwentyCreditsButton(true);
                writer.toggleFiftyCreditsButton(true);
                writer.toggleOneHundredCreditsButton(true);
                writer.toggleTwoHundredCreditsButton(true);
                writer.toggleFiveHundredCreditsButton(true);
            }
            else if(
                    (
                       ( ( player1.getCredit() < 500 ) && ( player2.getCredit() < 500 ) )
                    || ( ( player1.getCredit() < 500 ) && ( player2.getCredit() >= 500 ) )
                    || ( ( player1.getCredit() >= 500 ) && ( player2.getCredit() < 500 ) )
                    )
                    && ( player1.getCredit() >= 200  && player2.getCredit() >= 200 )
                   )
            {
                writer.toggleTenCreditsButton( true );
                writer.toggleTwentyCreditsButton( true );
                writer.toggleFiftyCreditsButton( true );
                writer.toggleOneHundredCreditsButton( true );
                writer.toggleTwoHundredCreditsButton( true );
                writer.toggleFiveHundredCreditsButton( false );
            }
            else if(
                    (
                       ( ( player1.getCredit() < 200 ) && ( player2.getCredit() < 200 ) )
                    || ( ( player1.getCredit() < 200 ) && ( player2.getCredit() >= 200 ) )
                    || ( ( player1.getCredit() >= 200 ) && ( player2.getCredit() < 200 ) )
                    )
                    && ( player1.getCredit() >= 100  && player2.getCredit() >= 100 )
                   )
            {
                writer.toggleTenCreditsButton( true );
                writer.toggleTwentyCreditsButton( true );
                writer.toggleFiftyCreditsButton( true );
                writer.toggleOneHundredCreditsButton( true );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }
            else if(
                    (
                       ( ( player1.getCredit() < 100 ) && ( player2.getCredit() < 100 ) )
                    || ( ( player1.getCredit() < 100 ) && ( player2.getCredit() >= 100 ) )
                    || ( ( player1.getCredit() >= 100 ) && ( player2.getCredit() < 100 ) )
                    )
                    && ( player1.getCredit() >= 50  && player2.getCredit() >= 50 )
                   )
            {
                writer.toggleTenCreditsButton( true );
                writer.toggleTwentyCreditsButton( true );
                writer.toggleFiftyCreditsButton( true );
                writer.toggleOneHundredCreditsButton( false );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }
            else if(
                    (
                       ( ( player1.getCredit() < 50 ) && ( player2.getCredit() < 50 ) )
                    || ( ( player1.getCredit() < 50 ) && ( player2.getCredit() >= 50 ) )
                    || ( ( player1.getCredit() >= 50 ) && ( player2.getCredit() < 50 ) )
                    )
                    && ( player1.getCredit() >= 20  && player2.getCredit() >= 20 )
                   )
            {
                writer.toggleTenCreditsButton( true );
                writer.toggleTwentyCreditsButton( true );
                writer.toggleFiftyCreditsButton( false );
                writer.toggleOneHundredCreditsButton( false );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }
            else if(
                    (
                       ( ( player1.getCredit() < 20 ) && ( player2.getCredit() < 20 ) )
                    || ( ( player1.getCredit() < 20 ) && ( player2.getCredit() >= 20 ) )
                    || ( ( player1.getCredit() >= 20 ) && ( player2.getCredit() < 20 ) )
                    )
                    && ( player1.getCredit() >= 10  && player2.getCredit() >= 10 )
                   )
            {
                writer.toggleTenCreditsButton( true );
                writer.toggleTwentyCreditsButton( false );
                writer.toggleFiftyCreditsButton( false );
                writer.toggleOneHundredCreditsButton( false );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }
        }
        else
        {
            gameFinished();
        }
    }

    // asks player 2 for bet
    private void askP2ForBet()
    {
        if( player1.getCredit() >= 500 && player2.getCredit() >= 500  )
        {
            currentBet = 250;
            player1.bet( currentBet );
            player2.bet( currentBet );
            writer.toggleCreditsBetLabel( currentBet * 2 );
            writer.toggleP1BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel( player1.getCredit(), player2.getCredit() );
        }
        else if( player1.getCredit() >= 200 && player2.getCredit() >= 200  )
        {
            currentBet = 100;
            player1.bet( currentBet );
            player2.bet( currentBet );
            writer.toggleCreditsBetLabel( currentBet * 2);
        }
        else if( player1.getCredit() >= 100 && player2.getCredit() >= 100  )
        {
            currentBet = 50;
            player1.bet( currentBet );
            player2.bet( currentBet );
            writer.toggleCreditsBetLabel( currentBet * 2 );
        }
        else if( player1.getCredit() >= 10 && player2.getCredit() >= 10 )
        {
            currentBet = 10;
            player1.bet( currentBet );
            player2.bet( currentBet );
            writer.toggleCreditsBetLabel( currentBet * 2 );
        }
        else
        {
            gameFinished();
        }

        firstRound();
    }

    // changes decks turns
    private CardDeck whichDeck()
    {
        CardDeck deck = null;
        int d = ( int )( Math.random() * 10 );

        if( ( d % 2 ) == 0 && deck1.moreCards() )
        {
            deck = deck1;
        }
        else if( deck2.moreCards() )
        {
            deck = deck2;
        }
        return deck;
    }

    // compares the players points
    private int comparePoints()
    {
        int result = 0;

        if( player1.getPoints() <= 21 && ( player1.getPoints() > player2.getPoints() || player2.getPoints() > 21 ) )
        {
            result = 1;
        }
        else if( player2.getPoints() <= 21 && ( player2.getPoints() > player1.getPoints() || player1.getPoints() > 21 ) )
        {
            result = 2;
        }
        else if( player1.getPoints() <= 21 && player2.getPoints() <= 21 && ( player1.getPoints() == player2.getPoints() ) )
        {
            result = 3;
        }

        return result;
    }

    // shows who won based on the current cards points
    private void showWhoWon()
    {
        writer.toggleRevealP2Cards( p2Cards );

        if( comparePoints() == 1 )
        {
            ImageIcon P1WonIcon = new ImageIcon( "img\\p1won.png" );
            JOptionPane.showMessageDialog( null, "    ","Round Over!", JOptionPane.INFORMATION_MESSAGE, P1WonIcon );

            p1roundsWon++;
            writer.toggleSetScore( p1roundsWon, p2roundsWon );
            player1.increaseCredit( currentBet * 2 );
            writer.toggleP1BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel( player1.getCredit(), player2.getCredit() );
        }
        else if( comparePoints() == 2 )
        {
            ImageIcon P2WonIcon = new ImageIcon( "img\\p2won.png" );
            JOptionPane.showMessageDialog( null, "","Round Over!", JOptionPane.INFORMATION_MESSAGE, P2WonIcon );

            p2roundsWon++;
            writer.toggleSetScore( p1roundsWon, p2roundsWon );
            player2.increaseCredit( currentBet * 2 );
            writer.toggleP1BankValueLabel(  player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel(  player1.getCredit(), player2.getCredit() );
        }
        else if( comparePoints() == 3 )
        {
            ImageIcon tieIcon = new ImageIcon( "img\\tieIcon.png" );
            JOptionPane.showMessageDialog( null, "","Round Over!", JOptionPane.INFORMATION_MESSAGE, tieIcon );

            player1.increaseCredit( currentBet );
            player2.increaseCredit( currentBet );
            writer.toggleP1BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel( player1.getCredit(), player2.getCredit() );
        }
        else
        {
            JOptionPane.showMessageDialog( null, "Points > 21 " );
            player1.increaseCredit( currentBet );
            player2.increaseCredit( currentBet );
            writer.toggleP1BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel( player1.getCredit(), player2.getCredit() );
        }

        writer.toggleShowP1Cards( p1Cards );
        writer.toggleShowP2Cards( p2Cards );
        writer.toggleRevealP2Cards( p2Cards );
        currentBet = 0;
        writer.toggleCreditsBetLabel( currentBet );

        /*
        try
        {
            Thread.sleep( 500 );
        }
        catch ( InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
         */
    }

    // shows the winner, and finishes game
    private void gameFinished()
    {
        if( p1roundsWon > p2roundsWon )
        {
            writer.toggleShowWinner1Label();
        }
        else if( p2roundsWon > p1roundsWon )
        {
            writer.toggleShowWinner2Label();
        }
        else
        {
            JOptionPane.showMessageDialog( null,"Equal Points!" );
        }

        //System.exit(0 );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        // starts the game when clicked
        if( actionEvent.getSource() == writer.welcomeScreenButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            writer.welcomeButtonClicked = true;
            writer.toggleP2CardsPointsLabel( false, player2.getPoints() );
            writer.toggleShowScoreBoard();
            writer.toggleSetScore( p1roundsWon, p2roundsWon );
            writer.toggleP1BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleDeckIconLabel( cardsLeft );
            writer.repaint();
            playGame();
        }

        // finishes round when stand button clicked
        if( actionEvent.getSource() == writer.standButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            roundOver();
            writer.repaint();
        }

        // deals cards when stand button clicked
        if( actionEvent.getSource() == writer.hitButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            P1Round();
            writer.repaint();
        }

        //  bets credits and starts the round when deal button clicked
        if( actionEvent.getSource() == writer.dealButton )
        {
            writer.playSound( "img\\clickButton.wav" );

            if( currentBet != 0 )
            {
                player2.bet( currentBet );
            }

            writer.toggleP1BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel( player1.getCredit(), player2.getCredit() );

            writer.toggleCurrentBetLabel();
            writer.toggleDealButton();
            writer.toggleWithdrawButton();
            writer.toggleTenCreditsButton( false );
            writer.toggleTwentyCreditsButton( false );
            writer.toggleFiftyCreditsButton( false );
            writer.toggleOneHundredCreditsButton( false );
            writer.toggleTwoHundredCreditsButton( false );
            writer.toggleFiveHundredCreditsButton( false );
            writer.currentBetLabel.setText( "Bet: 00" );
            firstRound();
        }

        // withdraws the money when withdraw button clicked
        if( actionEvent.getSource() == writer.withdrawButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            player1.increaseCredit( currentBet );
            currentBet = 0;
            writer.currentBetLabel.setText( "Chips: " + currentBet );
            writer.creditsBetLabel.setText( currentBet + " credits bet" );
            writer.toggleCreditsBetLabel( currentBet );
            writer.toggleP1BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.toggleP2BankValueLabel( player1.getCredit(), player2.getCredit() );
            writer.repaint();
        }

        // bets ten credits when ten button clicked
        if( actionEvent.getSource() == writer.tenCreditsButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            currentBet += Player.TEN_CREDITS;
            writer.toggleCreditsBetLabel( currentBet * 2 );
            writer.currentBetLabel.setText( "Chips: " + currentBet );

            player1.bet( Player.TEN_CREDITS );
            if( player1.getCredit() < Player.TEN_CREDITS )
            {
                writer.toggleTenCreditsButton( false );
                writer.toggleTwentyCreditsButton( false );
                writer.toggleFiftyCreditsButton( false );
                writer.toggleOneHundredCreditsButton( false );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }

            writer.repaint();
        }

        // bets ten credits when twenty button clicked
        if( actionEvent.getSource() == writer.twentyCreditsButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            currentBet += Player.TWENTY_CREDITS;
            writer.toggleCreditsBetLabel( currentBet * 2 );
            writer.currentBetLabel.setText( "Chips: " + currentBet );

            player1.bet( Player.TWENTY_CREDITS );
            if( player1.getCredit() < Player.TWENTY_CREDITS )
            {
                writer.toggleTwentyCreditsButton( false );
                writer.toggleFiftyCreditsButton( false );
                writer.toggleOneHundredCreditsButton( false );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }

            writer.repaint();
        }

        // bets fifty credits when ten button clicked
        if( actionEvent.getSource() == writer.fiftyCreditsButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            currentBet += Player.FIFTY_CREDITS;
            writer.toggleCreditsBetLabel( currentBet * 2 );
            writer.currentBetLabel.setText( "Chips: " + currentBet );

            player1.bet( Player.FIFTY_CREDITS );
            if( player1.getCredit() < Player.FIFTY_CREDITS )
            {
                writer.toggleFiftyCreditsButton( false );
                writer.toggleOneHundredCreditsButton( false );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }

            writer.repaint();
        }

        // bets ten credits when one hundred button clicked
        if( actionEvent.getSource() == writer.oneHundredCreditsButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            currentBet += Player.ONE_HUNDRED_CREDITS;
            writer.toggleCreditsBetLabel( currentBet * 2 );
            writer.currentBetLabel.setText( "Chips: " + currentBet );

            player1.bet( Player.ONE_HUNDRED_CREDITS );
            if( player1.getCredit() < Player.ONE_HUNDRED_CREDITS )
            {
                writer.toggleOneHundredCreditsButton( false );
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }

            writer.repaint();
        }

        // bets ten credits when two hundred button clicked
        if( actionEvent.getSource() == writer.twoHundredCreditsButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            currentBet += Player.TWO_HUNDRED_CREDITS;
            writer.toggleCreditsBetLabel( currentBet * 2 );
            writer.currentBetLabel.setText( "Chips: " + currentBet );

            player1.bet( Player.TWO_HUNDRED_CREDITS );
            if( player1.getCredit() < Player.TWO_HUNDRED_CREDITS )
            {
                writer.toggleTwoHundredCreditsButton( false );
                writer.toggleFiveHundredCreditsButton( false );
            }

            writer.repaint();
        }

        // bets ten credits when five hundred button clicked
        if( actionEvent.getSource() == writer.fiveHundredCreditsButton )
        {
            writer.playSound( "img\\clickButton.wav" );
            currentBet += Player.FIVE_HUNDRED_CREDITS;
            writer.toggleCreditsBetLabel( currentBet * 2 );
            writer.currentBetLabel.setText( "Chips: " + currentBet );

            player1.bet( Player.FIVE_HUNDRED_CREDITS );
            if( player1.getCredit() < Player.FIVE_HUNDRED_CREDITS )
            {
                writer.toggleFiveHundredCreditsButton(false);
            }

            writer.repaint();
        }
    }
}