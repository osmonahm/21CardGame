import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

/**
  * Class GameWriter is the view class of the BlackJack game, that extends JPanel, which is used to show the
  * user interface of the game, which the user interacts with, using data provided from the model class.
  *
  * @author Osmon
  */

public class GameWriter extends JPanel
{
    private final int frameWidth = 1000;    // frame width
    private final int frameHeight = 700;    // frame height

    protected boolean P1CardsTurn = false;  // player1's turn for showing cards
    protected boolean P2CardsTurn = false;  // player2's turn for showing cards
    protected boolean P2CardsTurn2 = false; // player2's turn for revealing cards
    private ArrayList<Card> P1Cards = new ArrayList<>();    // player1's deck of cards
    private ArrayList<Card> P1Cards2 = new ArrayList<>();    // player1's deck of cards
    private ArrayList<Card> P2Cards = new ArrayList<>();    // player2's deck of cards
    private int cardsLeft = 0;  // keeps track of the cards left

    // coordinates for showing the cards
    private int P1cardX = ( frameWidth / 2 ) - 70;
    private int P1cardY = 410;
    private int P2cardX = ( frameWidth / 2 ) - 70;
    private int P2cardY = 80;

    // data about the cards and the bank of both players
    private int P1Score = 0;
    private int P1Points = 0;
    private int P1BankValue = 0;
    private int P2Score = 0;
    private int P2Points = 0;
    private int P2BankValue = 0;
    private int currentCreditBet = 0;

    // the buttons shown in the GUI together with the booleans used to show them
    protected JButton welcomeScreenButton = new JButton( "Play" );
    protected boolean welcomeButtonClicked = false;
    protected JButton standButton = new JButton( "Stand" );
    private boolean standButtonTurn = false;
    protected JButton hitButton = new JButton( "Button" );
    private boolean hitButtonTurn = false;
    protected JButton dealButton = new JButton( "Deal" );
    private boolean dealButtonTurn = false;
    protected JButton withdrawButton = new JButton( "Withdraw" );
    private boolean withdrawButtonTurn = false;
    protected JButton tenCreditsButton = new JButton( "10" );
    private boolean tenCreditsButtonTurn = false;
    protected JButton twentyCreditsButton = new JButton( "20" );
    private boolean twentyCreditsButtonTurn = false;
    protected JButton fiftyCreditsButton = new JButton( "50" );
    private boolean fiftyCreditsButtonTurn = false;
    protected JButton oneHundredCreditsButton = new JButton( "50" );
    private boolean oneHundredCreditsButtonTurn = false;
    protected JButton twoHundredCreditsButton = new JButton( "50" );
    private boolean twoHundredCreditsButtonTurn = false;
    protected JButton fiveHundredCreditsButton = new JButton( "50" );
    private boolean fiveHundredCreditsButtonTurn = false;

    // the labels shown in the GUI together with the booleans used to show them
    private boolean showWinner1LabelTurn = false;
    protected JLabel showWinner1Label = new JLabel( "" );
    private boolean showWinner2LabelTurn = false;
    protected JLabel showWinner2Label = new JLabel( "" );
    private boolean setScoreTurn = false;
    protected JLabel scoreLabel = new JLabel( "0" );
    private boolean showScoreBoardTurn = false;
    protected JLabel P1CardsPointsLabel = new JLabel( "0" );
    private boolean P1CardsPointsTurn = false;
    protected JLabel P2CardsPointsLabel = new JLabel( "0" );
    private boolean P2CardsPointsTurn = false;
    protected JLabel P1BankValueLabel = new JLabel( "Bank: 1000" );
    private boolean P1BankValueLabelTurn = false;
    protected JLabel P2BankValueLabel = new JLabel( "Bank: 1000" );
    private boolean P2BankValueLabelTurn = false;
    protected JLabel creditsBetLabel = new JLabel( "00" );
    private boolean creditsBetLabelTurn = false;
    protected JLabel currentBetLabel = new JLabel( "Chips:  00" );
    private boolean currentBetLabelTurn = false;
    protected JLabel deckIconLabel = new JLabel( "Deck" );
    private boolean deckIconLabelTurn = false;

    /*
    protected JLabel P1WonLabel = new JLabel( "." );
    private boolean P1WonLabelTurn = false;
    protected JLabel P2WonLabel = new JLabel( "." );
    private boolean P2WonLabelTurn = false;
    protected JLabel tieLabel = new JLabel( "." );
    private boolean tieLabelTurn = false;

    */

    // Constructor creates and defines the frame
    public GameWriter()
    {
        JFrame frame = new JFrame();
        frame.setTitle( "BlackJack" );
        frame.getContentPane().add( this );
        frame.setSize( frameWidth, frameHeight );
        frame.setResizable( false );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }

    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );

        playButton();   // the button used to start the game

        ImageIcon welcomeScreen = new ImageIcon( "img\\ws.jpg" );   // welcome screen wallpaper
        welcomeScreen.paintIcon(this, g, 0, 0 );    // paints the welcome screen wallpaper

        // add a small delay when play button clicked and paints the game play background
        if( welcomeButtonClicked )
        {
            try
            {
                Thread.sleep(70 );
            }
            catch( InterruptedException ex )
            {
                Thread.currentThread().interrupt();
            }


            ImageIcon bgImg = new ImageIcon( "img\\bg1.jpg" );
            bgImg.paintIcon( this, g, 0, 0 );

            welcomeScreenButton.setVisible( false );
        }

        /*
        if( P1WonLabelTurn )
        {
            P1WonLabel();
        }

        if( P2WonLabelTurn )
        {
            P2WonLabel();
        }

        if( tieLabelTurn )
        {
            tieLabel();
        }
        */

        // used to show the first player won
        if( showWinner1LabelTurn )
        {
            showWinner1Label();
        }

        // used to show the second player won
        if( showWinner2LabelTurn )
        {
            showWinner2Label();
        }

        // used to show the score
        if( setScoreTurn )
        {
            setScore( g );
        }

        // used to show the player1 cards points
        if( P1CardsPointsTurn )
        {
            P1CardsPointsLabel();
        }

        // used to show the player2 cards points
        if( P2CardsPointsTurn )
        {
            P2CardsPointsLabel();
        }

        // used to show player1's bank value
        if( P1BankValueLabelTurn )
        {
            P1BankValueLabel();
        }

        // used to show player2's bank value
        if( P2BankValueLabelTurn )
        {
            P2BankValueLabel();
        }

        // used to show the score board
        if( showScoreBoardTurn )
        {
            showScoreBoard( g );
        }

        // used to show the deck of cards
        if( deckIconLabelTurn )
        {
            deckIconLabel();
        }

        // used to show the stand button
        if( standButtonTurn )
        {
            standButton.setVisible( true );
            standButton();
        }
        else
        {
            standButton.setVisible( false );
        }

        // used to show the hit button
        if( hitButtonTurn )
        {
            hitButton.setVisible( true );
            hitButton();
        }
        else
        {
            hitButton.setVisible( false );
        }

        // used to show the deal button
        if( dealButtonTurn )
        {
            dealButton( g );
        }

        // used to show the withdraw button
        if( withdrawButtonTurn )
        {
            withdrawButton();
        }

        // used to show the credits bet label
        if( creditsBetLabelTurn )
        {
            creditsBetLabel();
        }

        // used to show the current bet label
        if( currentBetLabelTurn )
        {
            currentBetLabel();
        }

        // used to show the ten credits button
        if( tenCreditsButtonTurn )
        {
            tenCreditsButton();
        }

        // used to show the twenty credits button
        if( twentyCreditsButtonTurn )
        {
            twentyCreditsButton();
        }

        // used to show the fifty credits button
        if( fiftyCreditsButtonTurn )
        {
            fiftyCreditsButton();
        }

        // used to show the one hundred credits button
        if( oneHundredCreditsButtonTurn )
        {
            oneHundredCreditsButton();
        }

        // used to show the two hundred credits button
        if( twoHundredCreditsButtonTurn )
        {
            twoHundredCreditsButton();
        }

        // used to show the five hundred credits button
        if( fiveHundredCreditsButtonTurn )
        {
            fiveHundredCreditsButton();
        }

        // used to reveal the player1's cards
        if( P1CardsTurn )
        {
            showP1Cards( g );
        }

        // used to show the player2's cards
        if( P2CardsTurn )
        {
            showP2Cards( g );
        }

        // used to reveal the player2's cards
        if( P2CardsTurn2 )
        {
            revealP2Cards( g );
        }
    }

    // defines the play button
    private void playButton()
    {
        welcomeScreenButton.setBounds( ( frameWidth / 2 ) - 110, 480, 220, 100 );
        welcomeScreenButton.setOpaque(false);
        welcomeScreenButton.setContentAreaFilled(false);
        welcomeScreenButton.setBorderPainted(false);

        ImageIcon playIcon = new ImageIcon( "img\\play3.png" );
        welcomeScreenButton.setIcon( playIcon );

        this.add( welcomeScreenButton );
    }

    // defines the sound the buttons make when clicked
    public void playSound( String soundName )
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( new File( soundName ).getAbsoluteFile( ) );
            Clip clip = AudioSystem.getClip( );
            clip.open( audioInputStream );
            clip.start( );
        }
        catch( Exception ex )
        {
            System.out.println( "Error with playing sound." );
            ex.printStackTrace( );
        }
    }

    /*
    protected void toggleP1WonLabel( boolean b )
    {
        P1WonLabelTurn = b;
        this.repaint();
    }

    private void P1WonLabel()
    {
        ImageIcon P1WonIcon = new ImageIcon( "img\\player1Won.png" );
        P1WonLabel.setIcon( P1WonIcon );
        P1WonLabel.setBounds( 385, 300, 200, 100 );
        P1WonLabel.setVisible( true );
        this.add( P1WonLabel );
    }

    protected void toggleP2WonLabel( boolean b )
    {
        P2WonLabelTurn = b;
        this.repaint();
    }

    private void P2WonLabel()
    {
        ImageIcon P2WonIcon = new ImageIcon( "img\\player2Won.png" );
        P2WonLabel.setIcon( P2WonIcon );
        P2WonLabel.setBounds( 385, 300, 200, 100 );
        P2WonLabel.setVisible( true );
        this.add( P2WonLabel );
    }

    protected void toggleTieLabel( boolean b )
    {
        tieLabelTurn = b;
        this.repaint();
    }

    private void tieLabel()
    {
        ImageIcon tieIcon = new ImageIcon( "img\\tie.png" );
        tieLabel.setIcon( tieIcon );
        tieLabel.setBounds( 385, 300, 200, 100 );
        tieLabel.setVisible( true );
        this.add( tieLabel );
    }

    */

    // triggers the winner1 label
    protected void toggleShowWinner1Label()
    {
        showWinner1LabelTurn = !showWinner1LabelTurn;
        this.repaint();
    }

    // shows that player1 won
    private void showWinner1Label()
    {
        ImageIcon winner1Icon = new ImageIcon( "img\\winner1.jpg" );
        showWinner1Label.setBounds( 0, 0, 1000, 700 );
        showWinner1Label.setIcon( winner1Icon );
        showWinner1Label.setVisible( true );
        this.add( showWinner1Label );
        this.repaint();
    }

    // triggers the winner2 label
    protected void toggleShowWinner2Label()
    {
        showWinner2LabelTurn = !showWinner2LabelTurn;
        this.repaint();
    }

    // shows that player2 won
    private void showWinner2Label()
    {
        ImageIcon winner1Icon = new ImageIcon( "img\\winner2.jpg" );
        showWinner2Label.setBounds( 0, 0, 1000, 700 );
        showWinner2Label.setIcon( winner1Icon );
        showWinner2Label.setVisible( true );
        this.add( showWinner2Label );
        this.repaint();
    }

    // triggers the score board label
    protected void toggleShowScoreBoard()
    {
        showScoreBoardTurn = !showScoreBoardTurn;
        this.repaint();
    }

    // shows the score board
    private void showScoreBoard( Graphics g )
    {
        ImageIcon scoreBoard = new ImageIcon( "img\\scoreBoard.png" );
        scoreBoard.paintIcon( this, g, 211, -57 );
    }

    // triggers the player1's points label
    protected void toggleP1CardsPointsLabel( boolean b, int points )
    {
        P1CardsPointsTurn = b;
        P1CardsPointsLabel.setVisible( b );
        P1Points = points;
        this.repaint();
    }

    // shows the player1's cards points
    private void P1CardsPointsLabel()
    {
        P1CardsPointsLabel.setBounds( 660, 500, 120, 30 );
        P1CardsPointsLabel.setFont( new Font( "ARIAL", Font.BOLD, 25 ) );
        P1CardsPointsLabel.setForeground( new Color(255, 255, 255) );
        P1CardsPointsLabel.setText( P1Points + " points" );
        P1CardsPointsLabel.setVisible( true );
        this.add( P1CardsPointsLabel );
    }

    // triggers the player2's points label
    protected void toggleP2CardsPointsLabel( boolean b, int points )
    {
        P2CardsPointsTurn = b;
        P2CardsPointsLabel.setVisible( b );
        P2Points = points;
        this.repaint();
    }

    // shows the player1's cards points
    private void P2CardsPointsLabel()
    {
        P2CardsPointsLabel.setBounds( 230, 190, 120, 30 );
        P2CardsPointsLabel.setFont( new Font( "ARIAL", Font.BOLD, 25 ) );
        P2CardsPointsLabel.setForeground( new Color( 255, 255, 255 ) );
        P2CardsPointsLabel.setText( P2Points + " points" );
        P2CardsPointsLabel.setVisible( true );
        this.add( P2CardsPointsLabel );
    }

    // triggers the score label
    protected void toggleSetScore( int p1, int p2 )
    {
        setScoreTurn = true;
        P1Score = p1;
        P2Score = p2;
        this.repaint();
    }

    // shows the current score
    private void setScore( Graphics g )
    {
        scoreLabel.setBounds( 450, 18, 120, 30 );
        scoreLabel.setFont( new Font( "ARIAL", Font.BOLD, 40 ) );
        scoreLabel.setForeground( new Color( 255, 255, 255 ) );
        scoreLabel.setText( P1Score + "    " + P2Score );
        scoreLabel.setVisible( true );
        this.add( scoreLabel );
    }

    // triggers the player1's bank value label
    protected void toggleP1BankValueLabel( int b1, int b2 )
    {
        P1BankValueLabelTurn = true;
        P1BankValue = b1;
        P2BankValue = b2;
        this.repaint();
    }

    // shows the current bank value of player1
    private void P1BankValueLabel()
    {
        P1BankValueLabel.setFont( new Font( "Verdana", Font.BOLD, 20 ) );
        P1BankValueLabel.setForeground( new Color(255, 255, 255) );
        P1BankValueLabel.setBounds( 45, 45, 160, 60 );
        P1BankValueLabel.setText( "Bank:" + P1BankValue );
        P1BankValueLabel.setVisible( true );
        this.repaint();
        this.add( P1BankValueLabel );
    }

    // triggers the player2's bank value label
    protected void toggleP2BankValueLabel( int b1, int b2 )
    {
        P2BankValueLabelTurn = true;
        P2BankValue = b1;
        P2BankValue = b2;
        this.repaint();
    }

    // shows the current bank value of player2
    private void P2BankValueLabel()
    {
        P2BankValueLabel.setFont( new Font( "Verdana", Font.BOLD, 20 ) );
        P2BankValueLabel.setForeground( new Color(255, 255, 255) );
        P2BankValueLabel.setBounds( frameWidth - 180, 45, 160, 60 );
        P2BankValueLabel.setText( "Bank:" + P2BankValue );
        P2BankValueLabel.setVisible( true );
        this.repaint();
        this.add( P2BankValueLabel );
    }

    // triggers the deck label
    protected void toggleDeckIconLabel( int c )
    {
        deckIconLabelTurn = true;
        cardsLeft = c;
        this.repaint();
    }

    // shows the deck of cards
    private void deckIconLabel()
    {
        ImageIcon deckIcon = new ImageIcon( "img\\deck.png" );
        deckIconLabel.setIcon( deckIcon );
        deckIconLabel.setBounds( 825, 50, 300, 191 );
        deckIconLabel.setFont( new Font( "Arial", Font.BOLD, 20 ) );
        deckIconLabel.setForeground( new Color( 255, 255, 255 ) );
        deckIconLabel.setText( cardsLeft + " cards" );
        deckIconLabel.setVisible( true );
        this.add( deckIconLabel );
    }

    // triggers the stand button
    protected void toggleStandButton( boolean b )
    {
        standButtonTurn = b;
        this.repaint();
    }

    // shows the stand button
    protected void standButton()
    {
        standButton.setText( "" );
        standButton.setBounds( ( frameWidth / 2 ) + 150, ( frameHeight / 2 ) - 35, 161, 56 );
        standButton.setOpaque( false );
        standButton.setContentAreaFilled( false );
        standButton.setBorderPainted( false );

        ImageIcon standIcon = new ImageIcon( "img\\standButton.png" );
        standButton.setIcon( standIcon );

        this.add( standButton );
    }

    // triggers the hit button
    protected void toggleHitButton( boolean b )
    {
        hitButtonTurn = b;
        this.repaint();
    }

    // shows the hit button
    protected void hitButton()
    {
        hitButton.setText( "" );
        hitButton.setBounds( ( frameWidth / 2 ) - 300, ( frameHeight / 2 ) - 35, 161, 56 );
        hitButton.setOpaque( false );
        hitButton.setContentAreaFilled( false );
        hitButton.setBorderPainted( false );

        ImageIcon hitButtonIcon = new ImageIcon( "img\\hitButton.png" );
        hitButton.setIcon( hitButtonIcon );

        this.add( hitButton );
    }

    // triggers the credits bet label
    protected void toggleCreditsBetLabel( int c )
    {
        creditsBetLabelTurn = true;
        currentCreditBet = c;
        this.repaint();
    }

    // shows the credits bet label
    private void creditsBetLabel()
    {
        ImageIcon chipIcon = new ImageIcon( "img\\chip.png" );
        creditsBetLabel.setIcon( chipIcon );
        creditsBetLabel.setBounds( 30, 50, 300, 191 );
        creditsBetLabel.setFont( new Font( "Arial", Font.BOLD, 20 ) );
        creditsBetLabel.setForeground( new Color( 255, 255, 255 ) );
        creditsBetLabel.setText( currentCreditBet + " credits bet" );
        creditsBetLabel.setVisible( true );
        this.add( creditsBetLabel );
    }

    // triggers the current bet label
    protected void toggleCurrentBetLabel()
    {
        if( currentBetLabelTurn )
        {
            currentBetLabelTurn = false;
            currentBetLabel.setVisible( false );
        }
        else
        {
            currentBetLabelTurn = true;
            this.repaint();
        }
    }

    // shows the current bet label
    private void currentBetLabel()
    {
        currentBetLabel.setFont( new Font( "Verdana", Font.BOLD, 20 ) );
        currentBetLabel.setForeground( new Color(255, 255, 255) );
        currentBetLabel.setBounds( 30, frameHeight - 225, 160, 60 );
        currentBetLabel.setVisible( true );

        this.add( currentBetLabel );
    }

    // triggers the deal button
    protected void toggleDealButton()
    {
        if( dealButtonTurn )
        {
            dealButtonTurn = false;
            dealButton.setVisible( false );
        }
        else
        {
            dealButtonTurn = true;
        }
        this.repaint();
    }

    // shows the deal button
    protected void dealButton( Graphics g )
    {
        ImageIcon dealIcon = new ImageIcon( "img\\deal.png" );
        dealButton.setIcon( dealIcon );
        dealButton.setFont( new Font( "Verdana", Font.PLAIN, 20 ) );
        dealButton.setBounds( 30, frameHeight - 175, 120, 40 );
        dealButton.setVerticalTextPosition( AbstractButton.CENTER );
        dealButton.setHorizontalTextPosition( AbstractButton.LEADING );
        dealButton.setVisible( true );

        ImageIcon boardIcon = new ImageIcon( "img\\chipsBoard.png" );
        boardIcon.paintIcon( this, g, 0, frameHeight - 289 );

        this.add( dealButton );
    }

    // triggers the withdraw button
    protected void toggleWithdrawButton()
    {
        if( withdrawButtonTurn )
        {
            withdrawButtonTurn = false;
            withdrawButton.setVisible( false );
        }
        else
        {
            withdrawButtonTurn = true;
        }
        this.repaint();
    }

    // shows the withdraw button
    protected void withdrawButton()
    {
        ImageIcon withdrawIcon = new ImageIcon( "img\\withdraw.png" );
        withdrawButton.setIcon( withdrawIcon );
        withdrawButton.setFont( new Font( "Verdana", Font.PLAIN, 10 ) );
        withdrawButton.setBounds( 30, frameHeight - 130, 120, 40 );
        //withdrawButton.setToolTipText( "Withdraw credits" );
        withdrawButton.setVerticalTextPosition( AbstractButton.CENTER );
        withdrawButton.setHorizontalTextPosition( AbstractButton.LEADING );
        withdrawButton.setVisible( true );
        this.add( withdrawButton );
    }

    // triggers the ten credits button
    protected void toggleTenCreditsButton( boolean b )
    {
        tenCreditsButtonTurn = b;
        tenCreditsButton.setVisible( b );

        this.repaint();
    }

    // shows the ten credits button
    protected void tenCreditsButton()
    {
        tenCreditsButton.setVisible( true );
        tenCreditsButton.setBounds( 170, frameHeight - 270, 100, 100 );
        tenCreditsButton.setOpaque( false );
        tenCreditsButton.setContentAreaFilled( false );
        tenCreditsButton.setBorderPainted( false );

        ImageIcon tenCreditsButtonIcon = new ImageIcon( "img\\chip10.png" );
        tenCreditsButton.setIcon( tenCreditsButtonIcon );

        this.add( tenCreditsButton );
    }

    // triggers the twenty credits button
    protected void toggleTwentyCreditsButton( boolean b )
    {
        twentyCreditsButtonTurn = b;
        twentyCreditsButton.setVisible( b );

        this.repaint();
    }

    // shows the twenty credits button
    protected void twentyCreditsButton()
    {
        twentyCreditsButton.setVisible( true );
        twentyCreditsButton.setBounds( 170, frameHeight - 160, 100, 100 );
        twentyCreditsButton.setOpaque( false );
        twentyCreditsButton.setContentAreaFilled( false );
        twentyCreditsButton.setBorderPainted( false );

        ImageIcon twentyCreditsButtonIcon = new ImageIcon( "img\\chip20.png" );
        twentyCreditsButton.setIcon( twentyCreditsButtonIcon );

        this.add( twentyCreditsButton );
    }

    // triggers the fifty credits button
    protected void toggleFiftyCreditsButton( boolean b )
    {
        fiftyCreditsButtonTurn = b;
        fiftyCreditsButton.setVisible( b );

        this.repaint();
    }

    // shows the fifty credits button
    protected void fiftyCreditsButton()
    {
        fiftyCreditsButton.setVisible( true );
        fiftyCreditsButton.setBounds( 320, frameHeight - 270, 100, 100 );
        fiftyCreditsButton.setOpaque( false );
        fiftyCreditsButton.setContentAreaFilled( false );
        fiftyCreditsButton.setBorderPainted( false );

        ImageIcon fiftyCreditsButtonIcon = new ImageIcon( "img\\chip50.png" );
        fiftyCreditsButton.setIcon( fiftyCreditsButtonIcon );

        this.add( fiftyCreditsButton );
    }

    // triggers the one hundred credits button
    protected void toggleOneHundredCreditsButton( boolean b )
    {
        oneHundredCreditsButtonTurn = b;
        oneHundredCreditsButton.setVisible( b );

        this.repaint();
    }

    // shows the one hundred credits button
    protected void oneHundredCreditsButton()
    {
        oneHundredCreditsButton.setVisible( true );
        oneHundredCreditsButton.setBounds( 320, frameHeight - 160, 100, 100 );
        oneHundredCreditsButton.setOpaque( false );
        oneHundredCreditsButton.setContentAreaFilled( false );
        oneHundredCreditsButton.setBorderPainted( false );

        ImageIcon oneHundredCreditsButtonIcon = new ImageIcon( "img\\chip100.png" );
        oneHundredCreditsButton.setIcon( oneHundredCreditsButtonIcon );

        this.add( oneHundredCreditsButton );
    }

    // triggers the two hundred credits button
    protected void toggleTwoHundredCreditsButton( boolean b )
    {
        twoHundredCreditsButtonTurn = b;
        twoHundredCreditsButton.setVisible( b );

        this.repaint();
    }

    // shows the two hundred credits button
    protected void twoHundredCreditsButton()
    {
        twoHundredCreditsButton.setVisible( true );
        twoHundredCreditsButton.setBounds( 470, frameHeight - 270, 100, 100 );
        twoHundredCreditsButton.setOpaque( false );
        twoHundredCreditsButton.setContentAreaFilled( false );
        twoHundredCreditsButton.setBorderPainted( false );

        ImageIcon twoHundredCreditsButtonIcon = new ImageIcon( "img\\chip200.png" );
        twoHundredCreditsButton.setIcon( twoHundredCreditsButtonIcon );

        this.add( twoHundredCreditsButton );
    }

    // triggers the five hundred credits button
    protected void toggleFiveHundredCreditsButton( boolean b )
    {
        fiveHundredCreditsButtonTurn = b;
        fiveHundredCreditsButton.setVisible( b );

        this.repaint();
    }

    // shows the five hundred credits button
    protected void fiveHundredCreditsButton()
    {
        fiveHundredCreditsButton.setVisible( true );
        fiveHundredCreditsButton.setBounds( 470, frameHeight - 160, 100, 100 );
        fiveHundredCreditsButton.setOpaque( false );
        fiveHundredCreditsButton.setContentAreaFilled( false );
        fiveHundredCreditsButton.setBorderPainted( false );

        ImageIcon fiveHundredCreditsButtonIcon = new ImageIcon( "img\\chip500.png" );
        fiveHundredCreditsButton.setIcon( fiveHundredCreditsButtonIcon );

        this.add( fiveHundredCreditsButton );
    }

    // triggers the revealing of player1's cards
    protected void toggleShowP1Cards( ArrayList<Card> c )
    {
        P1Cards = c;

        P1CardsTurn = !P1CardsTurn;
        this.repaint();
    }

    // triggers the revealing of player1's cards, based on the boolean b
    protected void toggleShowP1Cards( boolean b, ArrayList<Card> c )
    {
        P1Cards = c;

        P1CardsTurn = b;
        this.repaint();
    }

    // reveals the player1's cards
    private void showP1Cards( Graphics g )
    {
        ArrayList<ImageIcon> cardsImages = new ArrayList<>();
        String location = "img\\";

        for( int i = 0; i < P1Cards.size(); i++ )
        {
            cardsImages.add( new ImageIcon( location + P1Cards.get( i ).getCount() + "" + P1Cards.get( i ).getSuit().charAt( 0 ) + ".png" ) );
            Image image = cardsImages.get( i ).getImage();
            Image newImage = image.getScaledInstance( 140, 200, Image.SCALE_SMOOTH );
            ImageIcon img = new ImageIcon( newImage );
            cardsImages.remove( i );
            cardsImages.add( i, img );

            if( i == 0 )
            {
                cardsImages.get( i ).paintIcon( this, g, P1cardX - 30, P1cardY );
            }
            else if( i == 1 )
            {
                cardsImages.get( i ).paintIcon( this, g, P1cardX, P1cardY + 5);
            }
            else if( i == 2 )
            {
                cardsImages.get( i ).paintIcon( this, g, P1cardX + 30, P1cardY + 10 );
            }
            else if( i == 3 )
            {
                cardsImages.get( i ).paintIcon( this, g, P1cardX + 60, P1cardY + 15 );
            }
            else if( i == 4 )
            {
                cardsImages.get( i ).paintIcon( this, g, P1cardX + 90, P1cardY + 20 );
            }
        }
    }

    // triggers the showing of player2's cards
    protected void toggleShowP2Cards( ArrayList<Card> c )
    {
        P2Cards = c;

        P2CardsTurn = !P2CardsTurn;
        this.repaint();
    }

    // triggers the showing of player2's cards, based on the boolean b
    protected void toggleShowP2Cards( boolean b, ArrayList<Card> c )
    {
        P2Cards = c;

        P2CardsTurn = b;
        this.repaint();
    }

    // shows the player2's cards
    private void showP2Cards( Graphics g )
    {
        ArrayList<ImageIcon> cardsImages = new ArrayList<>();
        String location = "img\\back.png";

        for( int i = 0; i < P2Cards.size(); i++ )
        {
            cardsImages.add( new ImageIcon( location ) );
            Image image = cardsImages.get( i ).getImage();
            Image newImage = image.getScaledInstance( 140, 200, Image.SCALE_SMOOTH );
            ImageIcon img = new ImageIcon( newImage );
            cardsImages.remove( i );
            cardsImages.add( i, img );

            if( i == 0 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX - 30, P2cardY );
            }
            else if( i == 1 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX, P2cardY + 5 );
            }
            else if( i == 2 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX + 30, P2cardY + 10 );
            }
            else if( i == 3 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX + 60, P2cardY + 15 );
            }
            else if( i == 4 )
            {
                cardsImages.get( i ).paintIcon( this, g, P1cardX + 90, P1cardY + 20 );
            }
        }
    }

    // triggers the revealing of player2's cards
    protected void toggleRevealP2Cards( ArrayList<Card> c )
    {
        P2Cards = c;

        P2CardsTurn2 = !P2CardsTurn2;
        this.repaint();
    }

    // reveals the player2's cards
    private void revealP2Cards( Graphics g )
    {
        ArrayList<ImageIcon> cardsImages = new ArrayList<>();
        String location = "img\\";

        for( int i = 0; i < P2Cards.size(); i++ )
        {
            cardsImages.add( new ImageIcon( location + P2Cards.get( i ).getCount() + "" + P2Cards.get( i ).getSuit().charAt( 0 ) + ".png" ) );
            Image image = cardsImages.get( i ).getImage();
            Image newImage = image.getScaledInstance( 140, 200, Image.SCALE_SMOOTH );
            ImageIcon img = new ImageIcon( newImage );
            cardsImages.remove( i );
            cardsImages.add( i, img );

            if( i == 0 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX - 30, P2cardY );
            }
            else if( i == 1 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX, P2cardY + 5 );
            }
            else if( i == 2 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX + 30, P2cardY + 10 );
            }
            else if( i == 3 )
            {
                cardsImages.get( i ).paintIcon( this, g, P2cardX + 60, P2cardY + 15 );
            }
            else if( i == 4 )
            {
                cardsImages.get( i ).paintIcon( this, g, P1cardX + 90, P1cardY + 20 );
            }
        }
    }
}