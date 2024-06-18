package view.window;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * View class for the configuration window, extending WindowView with a GridPane.
 */
public class RuleView extends WindowView<GridPane> {

    /**
     * Constructs a ConfigView with a new GridPane.
     */
    public RuleView() {
        super(new GridPane(), 850, 715);

        // Configuring pane
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Creates the default pane for the configuration view, adding all necessary components.
     */
    @Override
    public void createDefaultPane() {
        TextArea textArea = new TextArea();
        textArea.setText("""
                FR :
                2°/ Régles
                                
                2.1°/ Déroulement du jeu
                                
                    Le premier joueur est déterminé par tirage au sort ou bien par accord.
                    Chacun son tour, chaque joueur choisit une action parmi les 3 possibles, expliquée ci-dessous
                    Cette alternance se poursuit jusqu'à la fin de partie, dont les conditions sont données en section 2.3
                                
                2.2°/ Actions de jeu
                                
                    Il existe 3 actions possibles :
                        piocher une carte déplacement,
                        jouer une carte déplacement seule,
                        jouer une carte déplacement plus une carte héros.
                                
                2.2.1°/ Piocher une carte déplacement
                                
                    Cette action n'est possible que si le joueur a moins de 5 cartes déplacement devant lui.
                    Il pioche la carte du dessus de la pioche et la place face visible, orientée de façon à avoir le numéro devant lui.
                    Si la pioche est vide au moment de piocher, il suffit  de mélanger la défausse pour refaire une pioche
                                
                2.2.2°/ Jouer une carte déplacement seule.
                                
                    Jouer une carte déplacement seule consiste à déplacer le roi dans la direction indiquée, du nombre de cases indiqué.
                    On ne peut pas jouer une carte :
                        dans une direction autre que celle indiquée par la carte,
                        avec moins de cases que le nombre indiqué par la carte,
                        si le déplacement fait sortir le roi du plateau,
                        si le déplacement amène le roi sur un pion (du joueur courant ou de l'adversaire).
                    A noter que le roi peut "sauter" par dessus des pions au cours de son déplacement.
                    Si la carte choisie par le joueur peut être jouée, alors :
                        il la pose dans une défausse à côté du plateau,
                        il pose un pion avec sa couleur visible sur la case d'arrivée,
                        il pose le roi sur le pion qu'il vient de poser dans la case d'arrivée.
                                
                2.2.3°/ Jouer une carte déplacement plus une carte héros
                                
                    Jouer un binôme de cartes déplacement+héros consiste à déplacer le roi dans la direction indiquée, du nombre de cases indiqué, pour atterrir sur une case occupée par un pion de la couleur adverse.
                    On ne peut pas jouer un tel binôme :
                        dans une direction autre que celle indiquée par la carte déplacement,
                        avec moins de cases que le nombre indiqué par la carte déplacement,
                        si le déplacement n'amène pas le roi sur un pion de l'adversaire.\s
                    A noter que le roi peut "sauter" par dessus des pions au cours de son déplacement.
                    Si le binôme de cartes choisi par le joueur peut être joué, alors :
                        il pose la carte déplacement dans une défausse à côté du plateau, et la carte héros dans une autre défausse. Il ne pourra jamais récupérer cette carte héros.
                        il retourne le pion de la case d'arrivé afin de changer sa couleur visible.
                        il pose le roi sur le pion qu'il vient de retourner dans la case d'arrivée.
                                
                \s
                                
                2.3°/ Fin de partie
                                
                    La partie s'arrête dans 2 cas :
                        dès qu'un joueur a 5 cartes déplacement posées devant lui et il ne peut en jouer aucune.
                        dès qu'un joueur pose le dernier pion disponible.
                    Pour déterminer le gagnant, il faut déterminer :
                        le nombre de zones "d'adjacence",
                        compter pour chaque zone le nombre de pions dans cette zone, et mettre au carré ce nombre pour obtenir la valeur de la zone
                        faire la somme des valeurs des zones.
                    Une zone d'adjacence est constituée par un ensemble de cases dont les jetons sont de la même couleur ET qui se touchent 2 à 2 par les côtés (pas par les coins).
                                
                \s
                                
                    Le joueur qui a la somme la plus élevée gagne la partie.
                    En cas d'égalité des sommes, le gagnant est celui qui a le plus de pions de sa couleur sur la plateau,
                    Si ce nombre de pions est égale, la partie est nulle et il n'y a pas de gagnants.
                                
                                
                                
                ENG :
                2°/ Rules
                                
                2.1°/ Flow of the game
                                
                    The first player is determined by drawing lots or by agreement.
                    In turn, each player chooses one of the 3 possible actions, explained below.
                    This alternation continues until the end of the game, the conditions of which are given in section 2.3.
                                
                2.2°/ Game actions
                                
                    There are 3 possible actions:
                        draw a move card,
                        play a move card alone,
                        play a move card plus a hero card.
                                
                2.2.1°/ Draw a move card
                                
                    This action is only possible if the player has less than 5 move cards in front of him.
                    He draws the top card from the deck and places it face-up, with the number in front of him.
                    If the deck is empty at the time of drawing, simply shuffle the discard pile to make a new deck.
                                
                2.2.2°/ Playing a move card alone.
                                
                    Playing a move card alone consists of moving the king in the indicated direction, by the indicated number of squares.
                    You cannot play a card :
                        in a direction other than that indicated by the card,
                        with fewer squares than the number indicated on the card,
                        if the move takes the king off the board,
                        if the move brings the king onto a pawn (of the current player or of the opponent).
                    Note that the king can "jump" over pawns during its move.
                    If the card chosen by the player can be played, then :
                        place it in a discard pile next to the board,
                        place a pawn with its color visible on the arrival square,
                        he places the king on the pawn he has just placed on the arrival square.
                                
                2.2.3°/ Playing a move card plus a hero card
                                
                    Playing a pair of move+hero cards consists in moving the king in the indicated direction, by the indicated number of squares, to land on a square occupied by a pawn of the opponent's color.
                    Such a pair cannot be played :
                        in a direction other than that indicated on the move card,
                        with fewer squares than the number indicated on the move card,
                        if the move does not place the king on an opponent's pawn.
                    Note that the king can "jump" over pawns during its move.
                    If the pair of cards chosen by the player can be played, then :
                        he places the move card in a discard pile next to the board, and the hero card in another discard pile. The hero card can never be retrieved.
                        he flips the pawn on the arrival square to change its visible color.
                        he places the king on the pawn he has just returned to the arrival square.
                                
                2.3°/ End of game
                                
                    The game ends in 2 cases:
                        as soon as a player has 5 move cards in front of him/her, and cannot play any of them.
                        as soon as a player places the last pawn available.
                    To determine the winner, you need to determine :
                        the number of "adjacency" zones,
                        count the number of pawns in each zone, and square this number to obtain the zone value.
                        add up the zone values.
                    An adjacency zone is made up of a set of squares whose tokens are the same color AND which touch each other 2 by 2 on the sides (not on the corners).
                                
                    The player with the highest sum wins the game.
                    In the event of a tie, the winner is the player with the most tokens of his or her color on the board,
                    If this number is equal, the game is drawn and there are no winners.
                    """);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", 19));
        textArea.setPrefSize(800, 600);
        pane.add(textArea, 0, 0);
        pane.setPadding(new Insets(30, 30, 30, 30));
    }

}
