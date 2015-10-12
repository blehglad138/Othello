/**
 * OthelloRunner.java
 * 
 * Application that creates a new Othello game and plays it.
 * 
 * @author Patrick Lee
 * @author 5111742
 * @version 4/4/13
 * @author Period: 2
 * @author Assignment: GWOthello
 * 
 * @author Sources: None
 */
public class OthelloRunner
{
    /**
     * Well...the runner of the game which has the stirng info in order to
     * instantiate the game
     * 
     * @param args
     *            Arguments not defined
     */
    public static void main( String[] args )
    {
        PickLevels levels = new PickLevels();
        levels.setSize( 500, 255 );
        levels.setVisible( true );

        String info = "";
        while ( info.length() == 0 )
            info = levels.info();
        int loc = info.indexOf( ", ", 0 );
        int i2 = info.indexOf( ", ", loc );
        int i3 = info.indexOf( ", ", i2 + 2 );
        if ( info.contains( "network" ) )
        {
            NetworkGame game = new NetworkGame();
            game.playGame();
        }
        else
        {
            if ( Math.abs( i3 - info.length() - 1 ) <= 3 )
            {
                OthelloGame game = new OthelloGame( true, info.substring( 0,
                    loc ), info.substring( i2 + 2, i3 ) );
                game.playGame();
                if( game.getResult().contains(info.substring( 0,
                    loc )) && game.getOthelloPlayer()[1] instanceof MinimaxOthelloPlayer )
                {
                    HardWin H = new HardWin();
                    H.setVisible( true );
                    H.display();
                }
            }
            else
            {
                OthelloGame game = new OthelloGame( true,
                    info.substring( 0, loc ),
                    info.substring( i2 + 2, i3 ),
                    info.substring( i3 + 2, info.length() - 1 ) );
                game.playGame();

            }
        }

    }
}