package model;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Logger;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import control.ActionPoints;
import control.SimpleActionList;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.container.card.MovementCardStack;
import model.container.card.MovementCardStackPlayed;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;
import utils.Elements;

import java.util.*;

/**
 * KoRStageModel defines the model for the single stage in "The KoR". Indeed,
 * there are no levels in this game: a party starts and when it's done, the game is also done.
 * <p>
 * KoRStageModel must define all that is needed to manage a party : state variables and game elements.
 * In the present case, there are only 2 state variables that represent the number of pawns to play by each player.
 * It is used to detect the end of the party.
 * For game elements, it depends on what is chosen as a final UI design. For that demo, there are 12 elements used
 * to represent the state : the main board, 2 pots, 8 pawns, and a text for current player.
 * <p>
 * WARNING ! KoRStageModel DOES NOT create itself the game elements because it would prevent the possibility to mock
 * game element classes for unit testing purposes. This is why KoRStageModel just defines the game elements and the methods
 * to set this elements.
 * The instanciation of the elements is done by the KoRStageFactory, which uses the provided setters.
 * <p>
 * KoRStageModel must also contain methods to check/modify the game state when given events occur. This is the role of
 * setupCallbacks() method that defines a callback function that must be called when a pawn is put in a container.
 * This is done by calling onPutInContainer() method, with the callback function as a parameter. After that call, boardifier
 * will be able to call the callback function automatically when a pawn is put in a container.
 * NB1: callback functions MUST BE defined with a lambda expression (i.e. an arrow function).
 * NB2:  there are other methods to defines callbacks for other events (see onXXX methods in GameStageModel)
 * In "The KoR", everytime a pawn is put in the main board, we have to check if the party is ended and in this case, who is the winner.
 * This is the role of computePartyResult(), which is called by the callback function if there is no more pawn to play.
 */
public class KoRStageModel extends GameStageModel {

    private static final int[] dx = {1, -1, 0, 0};
    private static final int[] dy = {0, 0, 1, -1};

    // define stage game elements
    private KoRBoard board;

    private HeroCardStack blueHeroCardStack;
    private HeroCardStack redHeroCardStack;
    private HeroCard[] blueHeroCards;
    private HeroCard[] redHeroCards;

    private MovementCardStack movementCardStack;
    private MovementCardStackPlayed movementCardStackPlayed;
    private MovementCardSpread blueMovementCardsSpread;
    private MovementCardSpread redMovementCardsSpread;
    private MovementCard[] movementCards;

    private PawnPot bluePot;
    private PawnPot redPot;
    private Pawn[] bluePawns;
    private Pawn[] redPawns;

    private Pawn kingPawn;

    // TEXT
    private TextElement playerName;

    private TextElement actionDescription1;
    private TextElement actionDescription2;
    private TextElement actionDescription3;
    private TextElement actionDescription4;

    private TextElement movementCardStackText;
    private TextElement bluePawnText;
    private TextElement redPawnText;
    private TextElement blueHeroCardText;
    private TextElement redHeroCardText;


    public KoRStageModel(String name, Model model) {
        super(name, model);

        setupCallbacks();
    }

    public KoRBoard getBoard() {
        return board;
    }

    public void setBoard(KoRBoard board) {
        this.board = board;
        addContainer(board);
    }

    public HeroCardStack getBlueHeroCardStack() {
        return blueHeroCardStack;
    }

    public void setBlueHeroCardStack(HeroCardStack blueHeroCardStack) {
        this.blueHeroCardStack = blueHeroCardStack;
        addContainer(blueHeroCardStack);
    }

    public HeroCardStack getRedHeroCardStack() {
        return redHeroCardStack;
    }

    public void setRedHeroCardStack(HeroCardStack redHeroCardStack) {
        this.redHeroCardStack = redHeroCardStack;
        addContainer(redHeroCardStack);
    }

    public HeroCard[] getBlueHeroCards() {
        return blueHeroCards;
    }

    public void setBlueHeroCards(HeroCard[] blueHeroCards) {
        this.blueHeroCards = blueHeroCards;
        for (HeroCard blueHeroCard : blueHeroCards) {
            addElement(blueHeroCard);
        }
    }

    public HeroCard[] getRedHeroCards() {
        return redHeroCards;
    }

    public void setRedHeroCards(HeroCard[] redHeroCards) {
        this.redHeroCards = redHeroCards;
        for (HeroCard redHeroCard : redHeroCards) {
            addElement(redHeroCard);
        }
    }

    public MovementCardStack getMovementCardStack() {
        return movementCardStack;
    }

    public void setMovementCardStack(MovementCardStack movementCardStack) {
        this.movementCardStack = movementCardStack;
        addContainer(movementCardStack);
    }

    public MovementCardStackPlayed getMovementCardStackPlayed() {
        return movementCardStackPlayed;
    }

    public void setMovementCardStackPlayed(MovementCardStackPlayed movementCardStackPlayed) {
        this.movementCardStackPlayed = movementCardStackPlayed;
        addContainer(movementCardStackPlayed);
    }

    public List<MovementCard> getMovementCards(MovementCard.Owner owner) {
        final List<MovementCard> movementCardList = new ArrayList<>();
        for (MovementCard movementCard : movementCards) {
            if (movementCard.getOwner() == owner) movementCardList.add(movementCard);
        }
        return movementCardList;
    }

    public MovementCard[] getMovementCards() {
        return movementCards;
    }

    public void setMovementCards(MovementCard[] movementCards) {
        this.movementCards = movementCards;
        for (MovementCard movementCard : movementCards) {
            addElement(movementCard);
        }
    }

    public MovementCardSpread getBlueMovementCardsSpread() {
        return blueMovementCardsSpread;
    }

    public void setBlueMovementCardsSpread(MovementCardSpread blueMovementCardsSpread) {
        this.blueMovementCardsSpread = blueMovementCardsSpread;
        addContainer(blueMovementCardsSpread);
    }

    public MovementCardSpread getRedMovementCardsSpread() {
        return redMovementCardsSpread;
    }

    public void setRedMovementCardsSpread(MovementCardSpread redMovementCardsSpread) {
        this.redMovementCardsSpread = redMovementCardsSpread;
        addContainer(redMovementCardsSpread);
    }

    public PawnPot getBluePot() {
        return bluePot;
    }

    public void setBluePot(PawnPot bluePot) {
        this.bluePot = bluePot;
        addContainer(bluePot);
    }

    public PawnPot getRedPot() {
        return redPot;
    }

    public void setRedPot(PawnPot redPot) {
        this.redPot = redPot;
        addContainer(redPot);
    }

    public Pawn[] getBluePawns() {
        return bluePawns;
    }

    public void setBluePawns(Pawn[] bluePawns) {
        this.bluePawns = bluePawns;
        for (Pawn bluePawn : bluePawns) {
            addElement(bluePawn);
        }
    }

    public Pawn[] getRedPawns() {
        return redPawns;
    }

    public void setRedPawns(Pawn[] redPawns) {
        this.redPawns = redPawns;
        for (Pawn redPawn : redPawns) {
            addElement(redPawn);
        }
    }

    public Pawn getKingPawn() {
        return kingPawn;
    }

    public void setKingPawn(Pawn kingPawn) {
        this.kingPawn = kingPawn;
        addElement(kingPawn);
    }

    public TextElement getPlayerName() {
        return playerName;
    }

    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    public TextElement getActionDescription1() {
        return actionDescription1;
    }

    public void setActionDescription1(TextElement actionDescription1) {
        this.actionDescription1 = actionDescription1;
        addElement(actionDescription1);
    }

    public TextElement getActionDescription2() {
        return actionDescription2;
    }

    public void setActionDescription2(TextElement actionDescription2) {
        this.actionDescription2 = actionDescription2;
        addElement(actionDescription2);
    }

    public TextElement getActionDescription3() {
        return actionDescription3;
    }

    public void setActionDescription3(TextElement actionDescription3) {
        this.actionDescription3 = actionDescription3;
        addElement(actionDescription3);
    }

    public TextElement getActionDescription4() {
        return actionDescription4;
    }

    public void setActionDescription4(TextElement actionDescription4) {
        this.actionDescription4 = actionDescription4;
        addElement(actionDescription4);
    }

    public TextElement getMovementCardStackText() {
        return movementCardStackText;
    }

    public void setMovementCardStackText(TextElement movementCardStackText) {
        this.movementCardStackText = movementCardStackText;
        addElement(movementCardStackText);
    }

    public TextElement getBluePawnText() {
        return bluePawnText;
    }

    public void setBluePawnText(TextElement bluePawnText) {
        this.bluePawnText = bluePawnText;
        addElement(bluePawnText);
    }

    public TextElement getRedPawnText() {
        return redPawnText;
    }

    public void setRedPawnText(TextElement redPawnText) {
        this.redPawnText = redPawnText;
        addElement(redPawnText);
    }

    public TextElement getBlueHeroCardText() {
        return blueHeroCardText;
    }

    public void setBlueHeroCardText(TextElement blueHeroCardText) {
        this.blueHeroCardText = blueHeroCardText;
        addElement(blueHeroCardText);
    }

    public TextElement getRedHeroCardText() {
        return redHeroCardText;
    }

    public void setRedHeroCardText(TextElement redHeroCardText) {
        this.redHeroCardText = redHeroCardText;
        addElement(redHeroCardText);
    }


    // READ ACTION
    private void setupCallbacks() {
        onRemoveFromContainer((element, containerFrom, rowDest, colDest) -> {
            // ACTION : Joue une carte héro
            if (element instanceof HeroCard heroCard) {
                // MET À JOUR LE COMPTEUR DE CARTE HÉRO
                final TextElement textElement;
                if (heroCard.getStatus() == HeroCard.Status.BLUE_CARD) {
                    textElement = blueHeroCardText;
                } else {
                    textElement = redHeroCardText;
                }
                Elements.updateText(textElement, ContainerElements.countElements(containerFrom));
            }
        });

        onPutInContainer((element, containerDest, rowDest, colDest) -> {
            // ACTION : Prendre une carte mouvement de la pile
            if (containerDest instanceof MovementCardSpread) {
                // CHANGE LE POSSESSEUR DE LA CARTE
                final MovementCard.Owner owner = (containerDest == blueMovementCardsSpread)
                        ? MovementCard.Owner.PLAYER_BLUE : MovementCard.Owner.PLAYER_RED;
                final MovementCard movementCard = (MovementCard) element;
                movementCard.setOwner(owner);

                // SI IL N'Y A PLUS DE CARTE DANS LA PILE ALORS LA REFAIRE
                if (getMovementCards(MovementCard.Owner.STACK).isEmpty()) redoMovementCardStack();

                // MET À JOUR LE COMPTEUR DE LA PILE
                movementCardStackText.setText(String.valueOf(ContainerElements.countElements(movementCardStack)));
            }

            // ACTION : Ajouter une carte de la fosse
            else if (containerDest == movementCardStackPlayed) {
                ((MovementCard)element).setOwner(MovementCard.Owner.OUT);
            }

            // ACTION : Ajouter une carte dans la pile
            else if(containerDest == movementCardStack) {
                final MovementCard movementCard = (MovementCard) element;
                movementCard.setOwner(MovementCard.Owner.STACK);
                movementCardStackText.setText(String.valueOf(ContainerElements.countElements(movementCardStack)));
            }

            // ACTION : Placer un pion sur le plateau
            else if (containerDest == board) {
                Pawn pawn = (Pawn) element;

                if (pawn.getStatus() == Pawn.Status.KING_PAWN) return;

                // MET À JOUR L'AFFICHAGE DES COMPTEURS DES PIONS
                redPawnText.setText(String.valueOf(ContainerElements.countElements(redPot)));
                bluePawnText.setText(String.valueOf(ContainerElements.countElements(bluePot)));
            }
        });
    }

    /**
     * @param playerData the player data
     * @return List of list of actions for the player to play a hero card
     */
    public List<ActionPoints> getPossibleHeroMove(PlayerData playerData, PlayerData opponent){
        final List<ActionPoints> actions = new ArrayList<>();
        if (playerData == null) return actions;

        final SimpleActionList simpleActionList = new SimpleActionList(model);

        final PawnPot pawnPot = getGeneralPot(playerData);
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = blueMovementCardsSpread;
            heroCardStack = blueHeroCardStack;
        } else {
            movementCardSpread = redMovementCardsSpread;
            heroCardStack = redHeroCardStack;
        }

        // SI LE JOUEUR N'A PLUS DE PION OU DE CARTE HERO, NE RENVOI AUCUNE ACTION
        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        if (pawnPot.isEmpty()||!hasHeroCard) return actions;

        final int countMovementCards = ContainerElements.countElements(movementCardSpread);

        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            // S'IL N'Y A PAS DE CARTE A CET EMPLACEMENT, PASSER A L'EMPLACEMENT SUIANT
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // RÉCUPÈRE CHAQUE CARTE DIRECTION DU JOUEUR
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);

            // RÉCUPÈRE L'EMPLACEMENT POTENTIEL DU ROI AVEC LA CARTE DIRECTION JOUÉE
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA POSITION POTENTIELLE OU QU'IL N'Y A PAS DE PION
            // ALORS ON PASSE À LA CARTE SUIVANTE
            if (!board.canReachCell(row, col)||board.isEmptyAt(row, col)) continue;

            // SI LE PION N'EST PAS CELUI DU JOUEUR
            // ALORS RAJOUTER L'ACTION DE LA CARTE DÉPLACEMENT + HERO
            if (!((Pawn) board.getElement(row, col)).getStatus().isOwnedBy(playerData)) {
                actions.add(new ActionPoints(simpleActionList.useHeroCard((HeroCard) heroCardStack.getElement(0, 0), movementCard,
                        (Pawn) board.getElement(row, col), potentialPos),getPlayerZonePawnSimple(opponent,row,col)));
            }

        }
        return actions;
    }

    /**
     * @param playerData the player data
     * @return List of list of actions for the player to play a movement card
     */
    public List<ActionPoints> getPossibleMovementCards(PlayerData playerData) {
        final List<ActionPoints> actions = new ArrayList<>();
        if (playerData == null) return actions;

        final SimpleActionList simpleActionList = new SimpleActionList(model);

        final PawnPot pawnPot = getGeneralPot(playerData);
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = blueMovementCardsSpread;
            heroCardStack = blueHeroCardStack;
        } else {
            movementCardSpread = redMovementCardsSpread;
            heroCardStack = redHeroCardStack;
        }

        // SI LE JOUEUR N'A PLUS DE PION, ALORS IL NE PEUT RIEN FAIRE
        if (pawnPot.isEmpty()) return actions;

        final int countMovementCards = ContainerElements.countElements(movementCardSpread);

        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // RÉCUPÈRE CHAQUE CARTE DIRECTION DU JOUEUR
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // RÉCUPÈRE L'EMPLACEMENT POTENTIEL DU ROI AVEC LA CARTE DIRECTION JOUÉE
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA POSITION POTENTIEL, ALORS ON PASSE À LA CARTE SUIVANTE
            if (!board.canReachCell(row, col)) continue;

            // SI L'EMPLACEMENT EST VIDE, ALORS RAJOUTER L'ACTION DU DÉPLACEMENT SIMPLE
            if (board.isEmptyAt(row, col)) {
                actions.add(new ActionPoints(simpleActionList.useMovementCard(movementCard, potentialPos, playerData),getPlayerZonePawnSimple(playerData,row,col)));
            }
        }
        return actions;
    }

    public List<ActionList> getPossibleTakeCardAction(PlayerData playerData) {
        final List<ActionList> actions = new ArrayList<>();
        if (playerData == null) return actions;

        final SimpleActionList simpleActionList = new SimpleActionList(model);

        final PawnPot pawnPot = getGeneralPot(playerData);
        final MovementCardSpread movementCardSpread;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = blueMovementCardsSpread;
        } else {
            movementCardSpread = redMovementCardsSpread;
        }

        // SI LE JOUEUR N'A PLUS DE PION, ALORS IL NE PEUT RIEN FAIRE
        if (pawnPot.isEmpty()) return actions;

        // SI LE JOUEUR PEUT PIOCHER UNE CARTE DE MOUVEMENT
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if (countMovementCards < 5) {
            // RAJOUTER L'ACTION DE PIOCHER
            actions.add(simpleActionList.pickUpMovementCard(movementCardSpread));
        }

        return actions;
    }

    public List<ActionList> getPossiblePlayerActions(PlayerData playerData) {
        final List<ActionList> actions = new ArrayList<>();
        if (playerData == null) return actions;

        final SimpleActionList simpleActionList = new SimpleActionList(model);

        final PawnPot pawnPot = getGeneralPot(playerData);
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = blueMovementCardsSpread;
            heroCardStack = blueHeroCardStack;
        } else {
            movementCardSpread = redMovementCardsSpread;
            heroCardStack = redHeroCardStack;
        }

        // SI LE JOUEUR N'A PLUS DE PION, ALORS IL NE PEUT RIEN FAIRE
        if (pawnPot.isEmpty()) return actions;

        // SI LE JOUEUR PEUT PIOCHER UNE CARTE DE MOUVEMENT
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if (countMovementCards < 5) {
            // RAJOUTER L'ACTION DE PIOCHER
            actions.add(simpleActionList.pickUpMovementCard(movementCardSpread));
        }

        // SI LE JOUEUR NE POSSÈDE PAS DE CARTE MOUVEMENT ALORS RENVOYER L'ACTION DE PIOCHER UNIQUEMENT
        if (countMovementCards == 0) return actions;

        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // RÉCUPÈRE CHAQUE CARTE DIRECTION DU JOUEUR
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // RÉCUPÈRE L'EMPLACEMENT POTENTIEL DU ROI AVEC LA CARTE DIRECTION JOUÉE
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA POSITION POTENTIEL, ALORS ON PASSE À LA CARTE SUIVANTE
            if (!board.canReachCell(row, col)) continue;

            // SI L'EMPLACEMENT EST VIDE, ALORS RAJOUTER L'ACTION DU DÉPLACEMENT SIMPLE
            // SINON SI LE JOUEUR POSSÈDE AU MOINS UNE CARTE HERO ET QUE LE PION N'EST PAS
            // LE SIEN ALORS RAJOUTER L'ACTION DE LA CARTE DÉPLACEMENT + HÉRO
            if (board.isEmptyAt(row, col)) {
                actions.add(simpleActionList.useMovementCard(movementCard, potentialPos, playerData));
            } else if (hasHeroCard && !((Pawn) board.getElement(row, col)).getStatus().isOwnedBy(playerData)) {
                actions.add(simpleActionList.useHeroCard((HeroCard) heroCardStack.getElement(0, 0), movementCard,
                        (Pawn) board.getElement(row, col), potentialPos));
            }
        }
        return actions;
    }

    public boolean playerCanPlay(PlayerData playerData) {
        final PawnPot pawnPot = getGeneralPot(playerData);

        // SI LES JOUEURS N'ONT PLUS DE PION, ALORS ILS NE PEUVENT RIEN FAIRE
        if (pawnPot == null) return false;

        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = blueMovementCardsSpread;
            heroCardStack = blueHeroCardStack;
        } else {
            movementCardSpread = redMovementCardsSpread;
            heroCardStack = redHeroCardStack;
        }

        // SI LE JOUEUR PEUT PIOCHER UNE CARTE DE MOUVEMENT
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if (countMovementCards < 5) return true;

        // LE JOUEUR PEUT-IL JOUER UNE CARTE ? LESQUELS
        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // RÉCUPÈRE CHAQUE CARTE DIRECTION DU JOUEUR
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // RÉCUPÈRE L'EMPLACEMENT POTENTIEL DU ROI AVEC LA CARTE DIRECTION JOUÉE
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA POSITION POTENTIEL, ALORS ON PASSE À LA CARTE SUIVANTE
            if (!board.canReachCell(row, col)) continue;

            // RENVOIE VRAI SI LE JOUEUR PEUT JOUER UNE CARTE MOUVEMENT
            // OU SI IL PEUT LA JOUER AVEC UNE CARTE HÉRO
            if (board.isEmptyAt(row, col) || (hasHeroCard && !((Pawn) board.getElement(row, col)).getStatus().isOwnedBy(playerData))) {
                return true;
            }
        }
        return false;
    }

    public boolean gameIsStuck() {
        return !playerCanPlay(PlayerData.PLAYER_BLUE) && !playerCanPlay(PlayerData.PLAYER_RED);
    }


    public void computePartyResult() {
        final int idWinner;
        final int redZoneCounter = getTotalPlayerPoint(PlayerData.PLAYER_RED);
        final int blueZoneCounter = getTotalPlayerPoint(PlayerData.PLAYER_BLUE);

        board.resetReachableCells(true);

        final int redPawnPlaced = getTotalPawnOnBoard(Pawn.Status.RED_PAWN);
        final int bluePawnPlaced = getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN);

        if (redZoneCounter == blueZoneCounter) {
            if (redPawnPlaced == bluePawnPlaced) {
                // ÉGALITÉ PARFAITE
                idWinner = -1;
            } else {
                // RÉCUPÈRE LE JOUEUR QUI A LE PLUS DE PION DE SA COULEUR VISIBLE POUR LE METTRE GAGNANT
                final PlayerData winner = (redPawnPlaced > bluePawnPlaced) ? PlayerData.PLAYER_RED : PlayerData.PLAYER_BLUE;
                idWinner = winner.getId();
            }
        } else {
            // RÉCUPÈRE LE JOUEUR QUI A LE PLUS DE POINT DE ZONE POUR LE METTRE GAGNANT
            final PlayerData winner = (redZoneCounter > blueZoneCounter) ? PlayerData.PLAYER_RED : PlayerData.PLAYER_BLUE;
            idWinner = winner.getId();
        }

        System.out.println("Points rouge : " + redZoneCounter + ", pions total " + redPawnPlaced);
        System.out.println("Points bleu : " + blueZoneCounter + ", pions total " + bluePawnPlaced);

        // set the winner
        model.setIdWinner(idWinner);
        // stop de the game
        model.stopStage();
    }

    public int getTotalPlayerPointSimple(PlayerData playerData) {
        final int playerPoint = getTotalPlayerPoint(playerData);
        board.resetReachableCells(true);
        return playerPoint;
    }

    public int getPlayerZonePawnSimple(PlayerData playerData, int row, int col) {
        final int playerPoint = getPlayerZonePawn(playerData, row, col);
        board.resetReachableCells(true);
        return playerPoint;
    }

    private int getTotalPlayerPoint(PlayerData playerData) {
        int totalCounter = 0;
        for (int row = 0; row < board.getNbRows(); row++) {
            for (int col = 0; col < board.getNbCols(); col++) {

                final int total = getPlayerZonePawn(playerData, row, col);

                // AJOUTE AU COMPTEUR FINAL LE COMPTEUR DE VOISIN AU CARRÉ
                totalCounter += total * total;
            }
        }
        return totalCounter;
    }

    private int getPlayerZonePawn(PlayerData playerData, int row, int col) {
        final Pawn.Status status = Pawn.Status.getPawnStatus(playerData);
        final Deque<PawnNode> pawnNodes = new LinkedList<>();

        final Pawn pawn = getPlayedPawn(row, col);

        // SI IL N'Y A PAS DE PION, ALORS RENDRE LA CASE INATTEIGNABLE ET PASSER À LA CASSE SUIVANTE

        // SI ON NE PEUT PAS ATTEINDRE LA CELLULE OU QUE LE PION EST CELUI DE L'ADVERSAIRE,
        // ALORS PASSER À LA CELLULE SUIVANTE
        if (!board.canReachCell(row, col) || (pawn != null && pawn.getStatus() != status)) return 0;


        // RENDRE LE PION INATTEIGNABLE
        board.setCellReachable(row, col, false);

        // AJOUTE UNE REFERENCE DU PION DANS UNE LISTE ET INITIALISE LE COMPTEUR DE VOISIN
        pawnNodes.add(new PawnNode(status, row, col));
        int counter = 0;

        while (!pawnNodes.isEmpty()) {
            // TANT QUE LA LISTE N'EST PAS VIDE ON Y RAJOUTER LES RÉFÉRENCES DES PIONS VOISINS ATTEIGNABLES
            // DE MEME COULER PAR RAPPORT AU 1ER RÉFÉRENTIEL DE LA LISTE TOUT EN L'ENLEVANT DE LA LISTE
            // + ON INCRÉMENTE DE 1 LE COMPTEUR DE VOISIN

            counter++;
            pawnNodes.addAll(getNeighbors(pawnNodes.poll()));
        }

        return counter;
    }

    private List<PawnNode> getNeighbors(PawnNode pawnNode) {
        final List<PawnNode> neighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            final int row = dy[i] + pawnNode.row;
            final int col = dx[i] + pawnNode.col;

            // SI ON NE PEUT PAS ATTEINDRE LA CELLULE PROCHAINE OU QU'ELLE A DÉJÀ ÉTÉ ATTEINTE
            // ALORS NE RIEN FAIRE ET PASSER À LA CELLULE SUIVANTE
            if (!board.canReachCell(row, col)) continue;

            final Pawn pawn = getPlayedPawn(row, col);

            // SI LA CASSE EST VIDE, ALORS RENDRE LA CELLULE INATTEIGNABLE
            // (SI ON EST TOUJOURS DANS LE TABLEAU) ET PASSER À LA CASE VOISINE SUIVANTE
            if (pawn == null) {
                board.setCellReachable(row, col, false);
                continue;
            }

            // SI LE PION EST CELUI DE L'ADVERSAIRE ALORS PASSER À LA CASE VOISINE SUIVANTE
            if (pawn.getStatus() != pawnNode.status) continue;

            // AJOUTE LE PION À LA LISTE ET RENDRE LA CELLULE INATTEIGNABLE
            neighbors.add(new PawnNode(pawnNode.status, row, col));
            board.setCellReachable(row, col, false);
        }

        return neighbors;
    }

    private int getTotalPawnOnBoard(Pawn.Status status) {
        int totalPawn = 0;

        for (int row = 0; row < board.getNbRows(); row++) {
            for (int col = 0; col < board.getNbCols(); col++) {
                final Pawn pawn = getPlayedPawn(row, col);

                // SI IL N'Y A PAS DE PION, ALORS PASSER À LA CASSE SUIVANTE
                if (pawn == null) continue;

                // INCRÉMENTER LE COMPTEUR SI LE PION EST BIEN DE LA BONNE COULEUR
                if (pawn.getStatus() == status) totalPawn++;
            }
        }

        return totalPawn;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new KoRStageFactory(this);
    }

    private void redoMovementCardStack() {
        // RÉCUPÈRE LES CARTES MOVEMENT DÉJÀ JOUÉES
        List<MovementCard> movementCardList = getMovementCards(MovementCard.Owner.OUT);

        // MÉLANGER LES CARTES QUI ONT ÉTÉ JOUÉES
        Collections.shuffle(movementCardList, GameConfigurationModel.RANDOM);

        final ActionList actionList = new ActionList();

        // REMET LES CARTES JOUÉES DANS LA PILE
        for (MovementCard movementCard : movementCardList)
            actionList.addAll(ActionFactory.generatePutInContainer(model, movementCard, movementCardStack.getName(), 0, 0));

        new ActionPlayer(model, null, actionList).start();
    }

    public Pawn getPlayedPawn(int row, int col) {
        for (GameElement gameElement : board.getElements(row, col)) {
            final Pawn pawn = (Pawn) gameElement;
            if (pawn == kingPawn) continue;
            return pawn;
        }
        return null;
    }

    public PawnPot getPawnPot(PlayerData playerData) {
        return (playerData == PlayerData.PLAYER_RED) ? redPot : bluePot;
    }

    public PawnPot getGeneralPot(PlayerData playerData) {
        PawnPot pawnPot = getPawnPot(playerData);
        if(pawnPot.isEmpty()) {
            pawnPot = getPawnPot(playerData.getNextPlayerData());
            if(pawnPot.isEmpty()) return null;
        }
        return pawnPot;
    }

    public record PawnNode(Pawn.Status status, int row, int col) {
    }
}
